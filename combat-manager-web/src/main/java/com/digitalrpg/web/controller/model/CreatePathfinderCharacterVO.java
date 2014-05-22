package com.digitalrpg.web.controller.model;

import com.digitalrpg.domain.model.characters.SystemCharacter;
import com.digitalrpg.domain.model.characters.SystemProperties;
import com.digitalrpg.domain.model.characters.pathfinder.PathfinderCharacter;

public class CreatePathfinderCharacterVO extends CreateCharacterVO {

    private PathfinderDataVO pathfinder;

    public CreatePathfinderCharacterVO() {
        super();
    }

    public CreatePathfinderCharacterVO(SystemCharacter systemCharacter) {
        super(systemCharacter);
        pathfinder = new PathfinderDataVO((PathfinderCharacter) systemCharacter);
    }

    @Override
    public SystemProperties getSystemProperties() {
        return pathfinder.toSystemProperties();
    }

    public PathfinderDataVO getPathfinder() {
        return pathfinder;
    }

    public void setPathfinder(PathfinderDataVO pathfinder) {
        this.pathfinder = pathfinder;
    }

}
