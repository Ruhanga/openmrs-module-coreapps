package org.openmrs.module.coreapps.contextmodel;

import org.openmrs.Program;
import org.openmrs.api.context.Context;

/**
 * A very simple view of a Program, suitable for use in an app contextModel.
 * 
 */
public class ProgramContextModel {

    private String uuid;

    private String name;

    public ProgramContextModel(Program program) {
        this.uuid = program.getUuid();
        this.name = program.getConcept().getPreferredName(Context.getLocale()).getName();
    }

    public String getUuid() {
        return uuid;
    }

	public String getName() {
		return name;
	}

}
