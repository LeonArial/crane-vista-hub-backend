package com.example.cranevistahubbackend;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 用于接收设备更新请求的数据传输对象 (DTO)。
 * 只包含可能由传感器发送的字段。
 */
@Data
public class EquipmentUpdateRequest {

    private String code; // 设备编码，用于查找设备，必须提供

    private EquipmentStatus status;
    private Double cabinTempInside;
    private Double cabinTempOutside;
    private Double humidity;
    private Double windSpeed;
    private Double mixingTemp;
    private Double mixingSpeed;
    private Double productionRate;
    private Integer workingHours;

    /**
     * 将此更新请求中的非空字段应用到现有的 Equipment 实体上。
     * @param equipment 从数据库中查出的需要被更新的实体
     */
    public void applyTo(Equipment equipment) {
        equipment.setLastUpdate(LocalDateTime.now()); // 始终更新最后更新时间

        if (this.status != null) {
            equipment.setStatus(this.status);
        }
        if (this.cabinTempInside != null) {
            equipment.setCabinTempInside(this.cabinTempInside);
        }
        if (this.cabinTempOutside != null) {
            equipment.setCabinTempOutside(this.cabinTempOutside);
        }
        if (this.humidity != null) {
            equipment.setHumidity(this.humidity);
        }
        if (this.windSpeed != null) {
            equipment.setWindSpeed(this.windSpeed);
        }
        if (this.mixingTemp != null) {
            equipment.setMixingTemp(this.mixingTemp);
        }
        if (this.mixingSpeed != null) {
            equipment.setMixingSpeed(this.mixingSpeed);
        }
        if (this.productionRate != null) {
            equipment.setProductionRate(this.productionRate);
        }
        if (this.workingHours != null) {
            equipment.setWorkingHours(this.workingHours);
        }
    }
}
