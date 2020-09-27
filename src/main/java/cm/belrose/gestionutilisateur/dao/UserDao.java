/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cm.belrose.gestionutilisateur.dao;

import cm.belrose.gestionutilisateur.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author PC-NGNAWEN
 * Comme j'ai implementer l'interface JpaRepository plus besoin d'implementer les methodes CRUD par consequant pas besoin
 * de la classe UserDaoImpl
 */
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    User findByLogin(String login);

}
