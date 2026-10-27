# Stratégie de fetch et de cascade — AutoLoc

Choix retenus pour les associations JPA du modèle AutoLoc.

| Association | Fetch | Cascade | Justification |
|---|---|---|---|
| Contrat → Paiement | LAZY | ALL + orphanRemoval | Un paiement n'existe que rattaché à son contrat (composition) ; supprimer le contrat supprime ses paiements, et retirer un paiement de la liste le supprime en base. LAZY car la liste n'est utile que ponctuellement. |
| Agence → Vehicule | LAZY | Aucune | Un véhicule survit à la fermeture de son agence : aucune suppression en cascade. Une agence peut avoir de nombreux véhicules, il ne faut pas les charger systématiquement. |
| Agence → Employe | LAZY | Aucune | Un employé a une existence propre (affectation modifiable) ; charger tous les employés d'une agence à chaque lecture serait inutile. |
| Vehicule ↔ Equipement | LAZY | Aucune | Les équipements sont partagés entre véhicules : supprimer un véhicule ne doit pas supprimer un équipement utilisé ailleurs. Utilisation d'un Set pour éviter les doublons et table de jointure vehicule_equipement. |
| Client → Reservation | LAZY | PERSIST | Enregistrer un client avec de nouvelles réservations les enregistre aussi. Pas de REMOVE : l'historique des réservations ne doit pas disparaître avec le client. |
| Reservation → Vehicule | LAZY | Aucune | Un véhicule existe indépendamment de ses réservations ; relation unidirectionnelle, côté propriétaire dans Reservation. |
| Reservation ↔ Contrat | LAZY | ALL (côté Reservation) | Le contrat est issu de la réservation et ne vit pas sans elle (composition). Limite : sur le côté inverse d'un OneToOne, Hibernate ne peut pas toujours différer le chargement. |
| Vehicule → Maintenance | LAZY | PERSIST | Les maintenances sont créées avec le véhicule, mais l'historique de maintenance est conservé : pas de REMOVE. |

## Principes appliqués

- Tous les @ManyToOne sont forcés en LAZY (le défaut JPA est EAGER) afin d'éviter de charger une chaîne d'objets inutiles.
- Les collections (@OneToMany, @ManyToMany) restent en LAZY.
- La cascade REMOVE n'est utilisée que pour une vraie composition (Paiement, Contrat).
- Lombok ciblé (@Getter, @Setter), sans @Data, pour éviter les boucles infinies sur les associations bidirectionnelles.