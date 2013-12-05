DELETE from pathfinder_action;

INSERT INTO pathfinder_action(
            id, description, label, current, initial)
    VALUES 
    	(1, 'Did not play in this round yet.', 'Pendind', false, true),
    	(2, 'Currently Playing', 'In Process', true, false),
    	(3, 'Took action', 'Taken', false, false),
    	(4, 'Delayed playing', 'Delayed', false, false);
    
