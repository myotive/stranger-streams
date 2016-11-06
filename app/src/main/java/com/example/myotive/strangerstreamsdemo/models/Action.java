package com.example.myotive.strangerstreamsdemo.models;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

@Generated("org.jsonschema2pojo")
public class Action extends RealmObject {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("attack_bonus")
    @Expose
    private Integer attackBonus;
    @SerializedName("damage_dice")
    @Expose
    private String damageDice;
    @SerializedName("damage_bonus")
    @Expose
    private Integer damageBonus;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The desc
     */
    public String getDesc() {
        return desc;
    }

    /**
     *
     * @param desc
     * The desc
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     *
     * @return
     * The attackBonus
     */
    public Integer getAttackBonus() {
        return attackBonus;
    }

    /**
     *
     * @param attackBonus
     * The attack_bonus
     */
    public void setAttackBonus(Integer attackBonus) {
        this.attackBonus = attackBonus;
    }

    /**
     *
     * @return
     * The damageDice
     */
    public String getDamageDice() {
        return damageDice;
    }

    /**
     *
     * @param damageDice
     * The damage_dice
     */
    public void setDamageDice(String damageDice) {
        this.damageDice = damageDice;
    }

    /**
     *
     * @return
     * The damageBonus
     */
    public Integer getDamageBonus() {
        return damageBonus;
    }

    /**
     *
     * @param damageBonus
     * The damage_bonus
     */
    public void setDamageBonus(Integer damageBonus) {
        this.damageBonus = damageBonus;
    }

}