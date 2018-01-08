package fr.sacem.priam.batch.affectation.processor;

import fr.sacem.priam.batch.affectation.domain.CatalogueOctavItem;
import fr.sacem.priam.batch.affectation.mapper.PointsResult;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by fandis on 15/12/2017.
 */
public class CalculPointsProcessor implements ItemProcessor<PointsResult, PointsResult> {

    @Override
    public PointsResult process(PointsResult pointsResult) throws Exception {
        return pointsResult;
    }
}
