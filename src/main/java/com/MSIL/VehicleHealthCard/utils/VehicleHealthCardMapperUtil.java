package com.MSIL.VehicleHealthCard.utils;

import com.MSIL.VehicleHealthCard.DTOs.response.items.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleHealthCardMapperUtil {

    /**
     *
     * @param sourceList
     * @return
     */
    public List<JobCardInfo> mapJobCardInfoList(List<JobCardInfo> sourceList) {
        if (sourceList == null) return List.of();
        return sourceList.stream()
                .map(item -> {
                    JobCardInfo copy = new JobCardInfo();
                    copy.setRegNum(item.getRegNum());
                    copy.setVin(item.getVin());
                    copy.setParentGroup(item.getParentGroup());
                    copy.setDealerMapCd(item.getDealerMapCd());
                    copy.setLocCd(item.getLocCd());
                    copy.setCompFa(item.getCompFa());
                    copy.setRoNum(item.getRoNum());
                    copy.setSrvCd(item.getSrvCd());
                    copy.setRoDate(item.getRoDate());
                    copy.setCloseDate(item.getCloseDate());
                    copy.setSaAdv(item.getSaAdv());
                    copy.setModel(item.getModel());
                    copy.setWorkshopCd(item.getWorkshopCd());
                    copy.setDealerName(item.getDealerName());
                    copy.setCustomerName(item.getCustomerName());
                    copy.setOdometer(item.getOdometer());
                    copy.setVhcYn(item.getVhcYn());
                    return copy;
                })
                .toList();
    }

    /**
     *
     * @param sourceList
     * @return
     */
    public List<VehicleHealthDetail> mapVehicleHealthDetails(List<VehicleHealthDetail> sourceList) {
        if (sourceList == null) return List.of();
        return sourceList.stream()
                .map(item -> {
                    VehicleHealthDetail copy = new VehicleHealthDetail();
                    copy.setVehSys(item.getVehSys());
                    copy.setVehSubSys(item.getVehSubSys());
                    copy.setVehSubSubSys(item.getVehSubSubSys());
                    copy.setActionCode(item.getActionCode());
                    copy.setStatus(item.getStatus());
                    copy.setRemarks(item.getRemarks());
                    copy.setMinValue(item.getMinValue());
                    copy.setMaxValue(item.getMaxValue());
                    copy.setStandValue(item.getStandValue());
                    copy.setVariancePer(item.getVariancePer());
                    copy.setMeasuredValue(item.getMeasuredValue());
                    return copy;
                })
                .toList();
    }

    /**
     *
     * @param sourceList
     * @return
     */
    public List<SystemInfo> mapSystemInfoList(List<SystemInfo> sourceList) {
        if (sourceList == null) return List.of();
        return sourceList.stream()
                .map(item -> {
                    SystemInfo copy = new SystemInfo();
                    copy.setListCode(item.getListCode());
                    copy.setListDesc(item.getListDesc());
                    return copy;
                })
                .toList();
    }

    /**
     *
     * @param sourceList
     * @return
     */
    public List<SubSystemInfo> mapSubSystemInfoList(List<SubSystemInfo> sourceList) {
        if (sourceList == null) return List.of();
        return sourceList.stream()
                .map(item -> {
                    SubSystemInfo copy = new SubSystemInfo();
                    copy.setListCode(item.getListCode());
                    copy.setListDesc(item.getListDesc());
                    copy.setLift(item.getLift());
                    return copy;
                })
                .toList();
    }

    /**
     *
     * @param sourceList
     * @return
     */
    public List<SubSubSystemInfo> mapSubSubSystemInfoList(List<SubSubSystemInfo> sourceList) {
        if (sourceList == null) return List.of();
        return sourceList.stream()
                .map(item -> {
                    SubSubSystemInfo copy = new SubSubSystemInfo();
                    copy.setListCode(item.getListCode());
                    copy.setListDesc(item.getListDesc());
                    return copy;
                })
                .toList();
    }

}
