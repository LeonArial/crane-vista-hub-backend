package com.example.cranevistahubbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin(origins = "*") // 允许所有来源的跨域请求
public class EquipmentController {

    private final EquipmentRepository equipmentRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public EquipmentController(EquipmentRepository equipmentRepository, SimpMessagingTemplate messagingTemplate) {
        this.equipmentRepository = equipmentRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    /**
     * 接收传感器数据并更新设备信息，然后通过 WebSocket 推送更新。
     * @param updateRequest 包含设备编码和需要更新的字段
     * @return 更新后的设备信息
     */
    @PostMapping("/update")
    public Equipment updateEquipment(@RequestBody EquipmentUpdateRequest updateRequest) {
        // 根据 code 查找设备
        Optional<Equipment> equipmentOptional = equipmentRepository.findByCode(updateRequest.getCode());

        if (equipmentOptional.isPresent()) {
            Equipment equipmentToUpdate = equipmentOptional.get();
            
            // 更新收到的不为 null 的字段
            updateRequest.applyTo(equipmentToUpdate);
            
            // 保存到数据库
            Equipment updatedEquipment = equipmentRepository.save(equipmentToUpdate);

            // 推送完整的最新列表到 WebSocket topic
            List<Equipment> allEquipment = equipmentRepository.findAll();
            messagingTemplate.convertAndSend("/topic/equipment-updates", allEquipment);

            return updatedEquipment;
        } else {
            // 如果找不到设备，可以根据业务需求决定是抛出异常还是其他处理
            // 这里我们暂时返回 null 或抛出异常
            throw new RuntimeException("Equipment not found with code: " + updateRequest.getCode());
        }
    }
}
