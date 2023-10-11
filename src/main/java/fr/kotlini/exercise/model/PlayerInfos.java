package fr.kotlini.exercise.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@DatabaseTable(tableName = "players")
public class PlayerInfos
{
    @DatabaseField(id = true)
    private String uuid;
    @DatabaseField
    private String name, grade;
    @DatabaseField
    private int money, age;
}
