package tn.esprit.autoloc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.*;
import tn.esprit.autoloc.repository.AgenceRepository;
import tn.esprit.autoloc.repository.VehiculeRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class VehiculeRepositoryTest {

    @Autowired
    private VehiculeRepository vehiculeRepository;
    @Autowired
    private AgenceRepository agenceRepository;

    private Agence tunis;

    @BeforeEach
    void init() {
        tunis = creerAgence("Agence Centre", "Tunis");
        Agence sfax = creerAgence("Agence Sud", "Sfax");

        creerVehicule("111TU1", "Peugeot", "208", "90.00", StatutVehicule.DISPONIBLE, tunis);
        creerVehicule("222TU2", "Peugeot", "3008", "150.00", StatutVehicule.LOUE, tunis);
        creerVehicule("333TU3", "Renault", "Clio", "80.00", StatutVehicule.DISPONIBLE, sfax);
        creerVehicule("444TU4", "Renault", "Kangoo", "100.00", StatutVehicule.MAINTENANCE, null);
        creerVehicule("555TU5", "Toyota", "Yaris", "70.00", StatutVehicule.DISPONIBLE, sfax);
    }

    @Test
    void exercice1_findByMarque() {
        assertThat(vehiculeRepository.findByMarque("Peugeot")).hasSize(2);
    }

    @Test
    void exercice2_findByMarqueContaining() {
        assertThat(vehiculeRepository.findByMarqueContaining("eug")).hasSize(2);
    }

    @Test
    void exercice7_existsByImmatriculation() {
        assertThat(vehiculeRepository.existsByImmatriculation("111TU1")).isTrue();
        assertThat(vehiculeRepository.existsByImmatriculation("000XX0")).isFalse();
    }

    @Test
    void exercice12_between() {
        assertThat(vehiculeRepository.findByTarifJournalierBetween(
                new BigDecimal("80"), new BigDecimal("100"))).hasSize(3);
    }

    @Test
    void exercice13_triCroissant() {
        assertThat(vehiculeRepository.findAllByOrderByTarifJournalierAsc().get(0).getMarque())
                .isEqualTo("Toyota");
    }

    @Test
    void exercice20_statut() {
        assertThat(vehiculeRepository.findByStatut(StatutVehicule.DISPONIBLE)).hasSize(3);
    }

    @Test
    void exercice23_agenceVille() {
        assertThat(vehiculeRepository.findByAgence_Ville("Sfax")).hasSize(2);
    }

    @Test
    void exercice26_sansAgence() {
        assertThat(vehiculeRepository.findByAgenceIsNull()).hasSize(1);
    }

    @Test
    void exercice28_countParAgence() {
        assertThat(vehiculeRepository.countByAgence_IdAgence(tunis.getIdAgence())).isEqualTo(2);
    }

    private Agence creerAgence(String nom, String ville) {
        Agence a = new Agence();
        a.setNom(nom);
        a.setVille(ville);
        a.setAdresse("Adresse test");
        return agenceRepository.save(a);
    }

    private void creerVehicule(String immat, String marque, String modele, String tarif, StatutVehicule statut, Agence agence) {
        Vehicule v = new Vehicule();
        v.setImmatriculation(immat);
        v.setMarque(marque);
        v.setModele(modele);
        v.setCategorie(CategorieVehicule.CITADINE);
        v.setTarifJournalier(new BigDecimal(tarif));
        v.setStatut(statut);
        v.setAgence(agence);
        vehiculeRepository.save(v);
    }
}