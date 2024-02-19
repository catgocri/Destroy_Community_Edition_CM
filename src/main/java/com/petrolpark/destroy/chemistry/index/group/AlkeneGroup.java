package com.petrolpark.destroy.chemistry.index.group;

import com.petrolpark.destroy.chemistry.Atom;
import com.petrolpark.destroy.chemistry.GroupType;
import com.petrolpark.destroy.chemistry.index.DestroyGroupTypes;

public class AlkeneGroup extends SaturatedCarbonGroup {

    public AlkeneGroup(Atom highDegreeCarbon, Atom lowDegreeCarbon) {
        super(highDegreeCarbon, lowDegreeCarbon);
    };

    @Override
    public GroupType<? extends SaturatedCarbonGroup> getType() {
        return DestroyGroupTypes.ALKENE;
    };
    
}
