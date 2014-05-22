package com.digitalrpg.domain.model.characters.pathfinder;

import com.digitalrpg.domain.model.characters.SystemProperties;

public class PathfinderCharacterProperties implements SystemProperties {

    /*
     * All pathfinder specific properties go here. Util class for easily creating pathfinder
     * characters
     */
    private Integer ac;

    private Integer hp;

    private Integer ref;

    private Integer fort;

    private Integer will;

    private Integer cmb;

    private Integer cmd;

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
