package tn.esprit.autoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.StatutVehicule;
import tn.esprit.autoloc.domain.Vehicule;

import java.math.BigDecimal;
import java.util.List;

public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    // Recherche de véhicules par marque
    List<Vehicule> findByMarque(String marque);
    // Recherche de véhicules par marque contenant un mot
    List<Vehicule> findByMarqueContaining(String mot);
    // Recherche de véhicules dont la marque commence par une valeur
    List<Vehicule> findByMarqueStartingWith(String debut);
    // Recherche de véhicules dont la marque se termine par une valeur
    List<Vehicule> findByMarqueEndingWith(String fin);
    // Recherche de véhicules par marque sans tenir compte de la casse
    List<Vehicule> findByMarqueIgnoreCase(String marque);

    // Recherche des véhicules par modèle
    List<Vehicule> findByModele(String modele);
    // Vérifier si un véhicule existe avec une immatriculation donnée
    boolean existsByImmatriculation(String immatriculation);
    // Compter les véhicules d'une marque
    long countByMarque(String marque);

    // Recherche de Véhicules dont le tarif > valeur
    List<Vehicule> findByTarifJournalierGreaterThan(BigDecimal tarif);
    // Recherche de Véhicules dont le tarif < valeur
    List<Vehicule> findByTarifJournalierLessThan(BigDecimal tarif);
    // Recherche de Véhicules dont le tarif >= valeur
    List<Vehicule> findByTarifJournalierGreaterThanEqual(BigDecimal tarif);
    // Recherche de Véhicules dont le tarif est compris entre deux valeurs - min <= tarif <= max (bornes incluses)
    List<Vehicule> findByTarifJournalierBetween(BigDecimal min,BigDecimal max);

    // Recherche de Véhicules triés par tarif croissant
    List<Vehicule> findAllByOrderByTarifJournalierAsc();
    // Recherche de Véhicules triés par tarif décroissant
    List<Vehicule> findAllByOrderByTarifJournalierDesc();
    // Recherche de Véhicules par marque et tri par tarif croissant
    List<Vehicule> findByMarqueOrderByTarifJournalierAsc(String marque);

    // Recherche de véhicules par marque dont le tarif > valeur
    List<Vehicule> findByMarqueAndTarifJournalierGreaterThan(String marque, BigDecimal tarif);
    // Recherche de véhicules par marque ou par modèle
    List<Vehicule> findByMarqueOrModele(String marque,String modele);
    // Recherche de véhicules dont la marque figure dans la liste
    List<Vehicule> findByMarqueIn(List<String> marques);
    //Recherche de véhicules dont la marque ne figure pas dans la liste
    List<Vehicule> findByMarqueNotIn(List<String> marques);

    // Recherche de véhicules par statut
    List<Vehicule> findByStatut(StatutVehicule statut);
    // Recherche de véhicules disponibles et tri par tarif croissant
    List<Vehicule> findByStatutOrderByTarifJournalierAsc(StatutVehicule statut);

    // Recherche de véhicules appartenant à une agence (id)
    List<Vehicule> findByAgence_IdAgence(Long idAgence);
    // Recherche de véhicules appartenant à une agence et une ville donnée
    List<Vehicule> findByAgence_Ville(String ville);
    // Recherche de véhicules d'une agence dont le nom contient une valeur
    List<Vehicule> findByAgence_NomContaining(String Valeur);
    // Recherche de véhicules par agence et par marque
    List<Vehicule> findByAgence_IdAgenceAndMarque(Long idAgence, String marque);

    // Recherche de véhicules sans agence
    List<Vehicule> findByAgenceIsNull();
    // Recherche de véhicules avec agence
    List<Vehicule> findByAgenceIsNotNull();
    // Compter les véhicules d'une agence
    long countByAgence_IdAgence(Long IdAgence);
    // Vérifier si une agence possède des véhicules
    boolean existsByAgence_IdAgence(Long idAgence);
}