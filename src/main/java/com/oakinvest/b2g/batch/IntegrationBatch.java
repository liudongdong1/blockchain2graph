package com.oakinvest.b2g.batch;

import com.oakinvest.b2g.repository.bitcoin.BitcoinBlockRepository;
import com.oakinvest.b2g.service.IntegrationService;
import com.oakinvest.b2g.service.StatusService;
import com.oakinvest.b2g.service.bitcoin.BitcoindService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Tasks for integrating bitcoin.
 * Created by straumat on 31/10/16.
 */
@Component
public class IntegrationBatch {

	/**
	 * Logger.
	 */
	private final Logger log = LoggerFactory.getLogger(IntegrationBatch.class);

	/**
	 * Bitcoind service.
	 */
	@Autowired
	private BitcoindService bds;

	/**
	 * Bitcoin blcok repository.
	 */
	@Autowired
	private BitcoinBlockRepository bbr;

	/**
	 * Status service.
	 */
	@Autowired
	private StatusService status;

	/**
	 * Integration service.
	 */
	@Autowired
	private IntegrationService is;

	/**
	 * Import a bitcoin block.
	 */
	@Scheduled()
	public final void importNextBitcoinBlock() {
		log.info("Batch called");
		// Retrieving data.
		final long importedBlockCount = bbr.count();
		final long nextBlockToImport = importedBlockCount + 1;
		final long totalBlockCount = bds.getBlockCount().getResult();

		// Update status.
		status.setImportedBlockCount(importedBlockCount);
		status.setTotalBlockCount(totalBlockCount);

		// if there is another block to import, let's import it !
		if (importedBlockCount < totalBlockCount) {
			try {
				is.integrateBitcoinBlock(nextBlockToImport);
			} catch (Exception e) {
				status.addError("Error in block " + nextBlockToImport + " " + e.getMessage());
			}
		}
	}

}
