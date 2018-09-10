package fr.sacem.priam.batch.filiation.npu.processor;

import fr.sacem.priam.batch.filiation.npu.dao.JournalCatcmsRepository;
import fr.sacem.priam.batch.filiation.npu.domain.OeuvreFiliationNPU;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by embouazzar on 29/08/2018.
 */
public class OeuvreFiliationNPUProcessor implements ItemProcessor<OeuvreFiliationNPU, OeuvreFiliationNPU> {

    @Autowired
    private JournalCatcmsRepository journalCatcmsRepository;

    @Override
    public OeuvreFiliationNPU process(OeuvreFiliationNPU oeuvreFiliationNPU) throws Exception {
        journalCatcmsRepository.saveJournalCatcms(oeuvreFiliationNPU.getIde12(), oeuvreFiliationNPU.getIde12Rep());
        return oeuvreFiliationNPU;
    }
}
