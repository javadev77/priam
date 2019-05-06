package fr.sacem.priam.model.dao.jpa;


import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.Journal;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by monfleurm on 13/03/2018.
 */

@Transactional(value="transactionManager")
public class JournalTest extends AbstractDaoTest {
    public static final Pageable PAGEABLE = new Pageable() {
        @Override
        public int getPageNumber() {
            return 0;
        }

        @Override
        public int getPageSize() {
            return 3;
        }

        @Override
        public int getOffset() {
            return 0;
        }

        @Override
        public Sort getSort() {
            return null;
        }

        @Override
        public Pageable next() {
            return null;
        }

        @Override
        public Pageable previousOrFirst() {
            return null;
        }

        @Override
        public Pageable first() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }
    };
        @Autowired
        private JournalDao journalDao;

        @Test
        public void should_return_all_events(){
            Page<Journal> allEvents = journalDao.findAll(PAGEABLE);

            assertThat(allEvents).isNotNull();
        }

/*    @Test
    public void should_return_events_for_numprog(){
            String numprog = "180091";
        Page<Journal> allEvents = journalDao.findByNumProg(numprog, PAGEABLE);

        assertThat(allEvents).isNotNull();
    }*/
    }


