package org.openmrs.module.coreapps.htmlformentry;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.openmrs.messagesource.MessageSourceService;
import org.openmrs.module.emrapi.EmrApiProperties;
import org.openmrs.module.emrapi.diagnosis.CodedOrFreeTextAnswer;
import org.openmrs.module.emrapi.diagnosis.Diagnosis;
import org.openmrs.module.emrapi.diagnosis.DiagnosisMetadata;
import org.openmrs.module.htmlformentry.FormEntryContext;
import org.openmrs.test.BaseContextMockTest;
import org.openmrs.ui.framework.BasicUiUtils;
import org.openmrs.ui.framework.UiUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EncounterDiagnosesByObsElementTest extends BaseContextMockTest {

    @Mock
    MessageSourceService messageSourceService;

    @Before
    public void setUp() throws Exception {
        when(messageSourceService.getMessage(anyString())).thenReturn("Translated");
    }

    @Test
    public void generateHtml_shouldIncludeHiddenConceptSourceField() throws Exception {
        // setup
        EmrApiProperties emrApiProperties = mock(EmrApiProperties.class);
        FormEntryContext context = mock(FormEntryContext.class);
        when(context.getMode()).thenReturn(FormEntryContext.Mode.ENTER);
        UiUtils uiUtils = mock(UiUtils.class);
        when(uiUtils.includeFragment(eq("coreapps"), eq("diagnosis/encounterDiagnoses"), anyMap())).thenReturn("Some Fragment");
        EncounterDiagnosesByObsElement element = new EncounterDiagnosesByObsElement() {
            @Override
            List<Diagnosis> getExistingDiagnoses(FormEntryContext context, DiagnosisMetadata diagnosisMetadata) {
                return Arrays.asList(new Diagnosis(new CodedOrFreeTextAnswer("Some disease")));
            }
        };
        element.setEmrApiProperties(emrApiProperties);
        element.setConceptSource("ICPC2");
        element.setUiUtils(uiUtils);
        String hiddenField = "<input type=\"hidden\" id=\"concept-source\" value=\"ICPC2\"/>";

        // replay
        String html = element.generateHtml(context);

        // verify
        assertTrue(StringUtils.contains(html, hiddenField));
    }
}