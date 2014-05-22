package com.digitalrpg.web.controller.model;

import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacterProperties;

public class PathfinderDataVO implements SystemDataVO {

    private Integer ac;

    private Integer hp;

    private Integer ref;

    private Integer fort;

    private Integer will;

    private Integer cmb;

    private Integer cmd;

    public PathfinderDataVO() {

    }

    public PathfinderDataVO(PathfinderCharacter systemCharacter) {
        this.ac = systemCharacter.getAc();
        this.hp = systemCharacter.getHp();
        this.ref = systemCharacter.getRef();
        this.fort = systemCharacter.getFort();
        this.will = systemCharacter.getWill();
        this.cmb = systemCharacter.getCmb();
        this.cmd = systemCharacter.getCmd();
    }

    public PathfinderCharacterProperties toSystemProperties() {
        PathfinderCharacterProperties properties = new PathfinderCharacterProperties();
        properties.setAc(this.getAc());
        properties.setCmb(this.getCmb());
        properties.setCmd(this.getCmd());
        properties.setFort(this.getFort());
        properties.setHp(this.getHp());
        properties.setRef(this.getRef());
        properties.setWill(this.getWill());
        return properties;
    }

    public Integer getAc() {
        return ac;
    }

    public void setAc(Integer ac) {
        this.ac = ac;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getRef() {
        return ref;
    }

    public void setRef(Integer ref) {
        this.ref = ref;
    }

    public Integer getFort() {
        return fort;
    }

    public void setFort(Integer fort) {
        this.fort = fort;
    }

    public Integer getWill() {
        return will;
    }

    public void setWill(Integer will) {
        this.will = will;
    }

    public Integer getCmb() {
        return cmb;
    }

    public void setCmb(Integer cmb) {
        this.cmb = cmb;
    }

    public Integer getCmd() {
        return cmd;
    }

    public void setCmd(Integer cmd) {
        this.cmd = cmd;
    }


}
