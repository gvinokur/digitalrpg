package com.digitalrpg.domain.model.characters.pathfinder;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.SystemProperties;

@Entity
@Table(name = "pathfinder_character")
public class PathfinderCharacter extends SystemCharacter {


    private Integer ac;

    private Integer hp;

    private Integer ref;

    private Integer fort;

    private Integer will;

    private Integer cmb;

    private Integer cmd;


    public void updateProperties(SystemProperties properties) {
        if (properties == null || !(properties instanceof PathfinderCharacterProperties))
            return;
        PathfinderCharacterProperties pathfinderProperties =
                (PathfinderCharacterProperties) properties;
        this.setAc(pathfinderProperties.getAc());
        this.setCmb(pathfinderProperties.getCmb());
        this.setCmd(pathfinderProperties.getCmd());
        this.setFort(pathfinderProperties.getFort());
        this.setHp(pathfinderProperties.getHp());
        this.setRef(pathfinderProperties.getRef());
        this.setWill(pathfinderProperties.getWill());
    }


    public Integer getAc() {
        return ac;
    }


    public void setAc(Integer ac) {
        this.ac = ac;
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


    public void setHp(Integer hp) {
        this.hp = hp;
    }


    public int getHp() {
        return hp;
    }

}
