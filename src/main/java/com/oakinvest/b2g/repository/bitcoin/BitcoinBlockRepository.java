package com.oakinvest.b2g.repository.bitcoin;

import com.oakinvest.b2g.domain.bitcoin.BitcoinBlock;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * BitcoinBlock repository.
 * Created by straumat on 09/09/16.
 */
@Repository
public interface BitcoinBlockRepository extends Neo4jRepository<BitcoinBlock, Long> {

    /**
     * Find a block by its height.
     *
     * @param height height
     * @return block
     */
    Optional<BitcoinBlock> findByHeight(int height);

    /**
     * Find a block by its height.
     *
     * @param height height
     * @return block
     */
    @Query("MATCH (b:BitcoinBlock) WHERE b.height= {0} return b")
    Optional<BitcoinBlock> findByHeightWithoutDepth(int height);

    /**
     * Find a block by its hash.
     *
     * @param hash hash
     * @return block
     */
    Optional<BitcoinBlock> findByHash(String hash);

    /**
     * Find a block by its hash.
     *
     * @param hash hash
     * @return block
     */
    @Query("MATCH (b:BitcoinBlock) WHERE b.hash= {0} return b")
    Optional<BitcoinBlock> findByHashWithoutDepth(String hash);

}
