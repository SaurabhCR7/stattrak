package com.stattrak
package db

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.Row
import com.stattrak.models.{User, Userdata}
import com.stattrak.utils.Logging

import scala.jdk.CollectionConverters.*

object ScyllaDbClient extends Logging {
  private val keyspace = "stattrak"
  private val tableName = "valorant"
  private val cqlSession = CqlSession.builder.build

  def apply(): Unit = {
    try {
      val createKeyspaceQuery = s"CREATE KEYSPACE IF NOT EXISTS $keyspace WITH replication = " +
        s"{'class': 'SimpleStrategy', 'replication_factor' : 3};"

      val createTableQuery = s"CREATE COLUMNFAMILY IF NOT EXISTS $keyspace.$tableName " +
        s"(userId text PRIMARY KEY, channelId bigInt, matchId text, rank text);"

      cqlSession.execute(createKeyspaceQuery)
      cqlSession.execute(createTableQuery)
    } catch {
      case ex: Exception => error(s"Failed to create $keyspace.$tableName table in Database with error : ", ex)
        throw ex
    }
  }

  def getAllUsersWithData: List[(User, Userdata)] = {
    try {
      val query = s"SELECT * FROM $keyspace.$tableName;"
      val result = cqlSession.execute(query)
      result.all.asScala.map(row => (extractValorantUser(row), extractUserData(row))).toList
    } catch {
      case ex: Exception =>
        error("Failed to get users with data from Database with error : ", ex)
        throw ex
    }
  }

  def addUser(user: User, userdata: Userdata): Unit = {
    try {
      val query = s"INSERT INTO $keyspace.$tableName (userId, channelId, matchid, rank) " +
        s"VALUES ('$user', ${userdata.channelId}, '${userdata.matchId}', '${userdata.rank}');"
      cqlSession.execute(query)
      info(s"Successfully added user $user in Database")
    } catch {
      case ex: Exception =>
        error(s"Failed to add user $user in Database with error : ", ex)
        throw ex
    }
  }

  def removeUser(user: User): Unit = {
    try {
      val query = s"DELETE FROM $keyspace.$tableName WHERE userId = '$user';"
      cqlSession.execute(query)
      info(s"Successfully removed user $user from Database")
    } catch {
      case ex: Exception =>
        error(s"Failed to remove user $user in Database with error : ", ex)
        throw ex
    }
  }

  def updateMatchId(user: User, matchId: String): Unit = {
    try {
      val query = s"UPDATE $keyspace.$tableName SET matchId = '$matchId' WHERE userId = '$user';"
      cqlSession.execute(query)
      info(s"Successfully updated matchId for user $user in Database")
    } catch {
      case ex: Exception =>
        error(s"Failed to update matchId for user $user in Database with error : ", ex)
        throw ex
    }
  }


  // Utils :
  private def extractUserData(row: Row): Userdata = {
    Userdata(row.getLong("channelId"), row.getString("matchId"), row.getString("rank"))
  }

  private def extractValorantUser(row: Row): User = {
    val userId = row.getString("userId")
    val tempArray = userId.split("#")
    val username = tempArray(0)
    val tag = tempArray(1)
    User(username, tag)
  }
}

