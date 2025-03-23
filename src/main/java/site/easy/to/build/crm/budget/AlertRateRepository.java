package site.easy.to.build.crm.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AlertRateRepository extends JpaRepository<AlertRate, Long> {
    @Query("SELECT a.rate FROM AlertRate a ORDER BY a.id DESC LIMIT 1")
    BigDecimal findLatestRate();
}

