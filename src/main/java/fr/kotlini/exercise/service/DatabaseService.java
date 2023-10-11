package fr.kotlini.exercise.service;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import fr.kotlini.exercise.model.PlayerInfos;
import fr.kotlini.exercise.util.RandomUtils;
import lombok.SneakyThrows;

import java.sql.*;
import java.util.UUID;

public class DatabaseService
{
    private final Dao<PlayerInfos, String> playerInfosDao;

    public DatabaseService() throws SQLException {
        final JdbcConnectionSource connectionSource = new JdbcConnectionSource("jdbc:mysql://localhost:3306/end_mc",
                "root" ,"");
        playerInfosDao = DaoManager.createDao(connectionSource, PlayerInfos.class);
        TableUtils.createTableIfNotExists(connectionSource, PlayerInfos.class);
    }

    @SneakyThrows
    public PlayerInfos getOrCreate(UUID uuid, String name) {
        if (!playerInfosDao.idExists(uuid.toString())) {
            final PlayerInfos playerInfos = new PlayerInfos(uuid.toString(), name, RandomUtils.randomGrade(),
                    RandomUtils.randomMoney(), RandomUtils.randomAge());

            playerInfosDao.create(playerInfos);
            return playerInfos;
        }

        return playerInfosDao.queryForId(uuid.toString());
    }
}