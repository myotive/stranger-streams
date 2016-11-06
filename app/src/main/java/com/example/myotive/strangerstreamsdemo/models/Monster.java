package com.example.myotive.strangerstreamsdemo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

import io.realm.RealmList;
import io.realm.RealmObject;

@Generated("org.jsonschema2pojo")
public class Monster extends RealmObject {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("subtype")
    @Expose
    private String subtype;
    @SerializedName("alignment")
    @Expose
    private String alignment;
    @SerializedName("armor_class")
    @Expose
    private Integer armorClass;
    @SerializedName("hit_points")
    @Expose
    private Integer hitPoints;
    @SerializedName("hit_dice")
    @Expose
    private String hitDice;
    @SerializedName("speed")
    @Expose
    private String speed;
    @SerializedName("strength")
    @Expose
    private Integer strength;
    @SerializedName("dexterity")
    @Expose
    private Integer dexterity;
    @SerializedName("constitution")
    @Expose
    private Integer constitution;
    @SerializedName("intelligence")
    @Expose
    private Integer intelligence;
    @SerializedName("wisdom")
    @Expose
    private Integer wisdom;
    @SerializedName("charisma")
    @Expose
    private Integer charisma;
    @SerializedName("wisdom_save")
    @Expose
    private Integer wisdomSave;
    @SerializedName("damage_vulnerabilities")
    @Expose
    private String damageVulnerabilities;
    @SerializedName("damage_resistances")
    @Expose
    private String damageResistances;
    @SerializedName("damage_immunities")
    @Expose
    private String damageImmunities;
    @SerializedName("condition_immunities")
    @Expose
    private String conditionImmunities;
    @SerializedName("senses")
    @Expose
    private String senses;
    @SerializedName("languages")
    @Expose
    private String languages;
    @SerializedName("challenge_rating")
    @Expose
    private String challengeRating;
    @SerializedName("special_abilities")
    @Expose
    private RealmList<SpecialAbility> specialAbilities = new RealmList<SpecialAbility>();
    @SerializedName("actions")
    @Expose
    private RealmList<Action> actions = new RealmList<Action>();

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
     * The size
     */
    public String getSize() {
        return size;
    }

    /**
     *
     * @param size
     * The size
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     *
     * @return
     * The type
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @param type
     * The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @return
     * The subtype
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     *
     * @param subtype
     * The subtype
     */
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    /**
     *
     * @return
     * The alignment
     */
    public String getAlignment() {
        return alignment;
    }

    /**
     *
     * @param alignment
     * The alignment
     */
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    /**
     *
     * @return
     * The armorClass
     */
    public Integer getArmorClass() {
        return armorClass;
    }

    /**
     *
     * @param armorClass
     * The armor_class
     */
    public void setArmorClass(Integer armorClass) {
        this.armorClass = armorClass;
    }

    /**
     *
     * @return
     * The hitPoints
     */
    public Integer getHitPoints() {
        return hitPoints;
    }

    /**
     *
     * @param hitPoints
     * The hit_points
     */
    public void setHitPoints(Integer hitPoints) {
        this.hitPoints = hitPoints;
    }

    /**
     *
     * @return
     * The hitDice
     */
    public String getHitDice() {
        return hitDice;
    }

    /**
     *
     * @param hitDice
     * The hit_dice
     */
    public void setHitDice(String hitDice) {
        this.hitDice = hitDice;
    }

    /**
     *
     * @return
     * The speed
     */
    public String getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed
     * The speed
     */
    public void setSpeed(String speed) {
        this.speed = speed;
    }

    /**
     *
     * @return
     * The strength
     */
    public Integer getStrength() {
        return strength;
    }

    /**
     *
     * @param strength
     * The strength
     */
    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    /**
     *
     * @return
     * The dexterity
     */
    public Integer getDexterity() {
        return dexterity;
    }

    /**
     *
     * @param dexterity
     * The dexterity
     */
    public void setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
    }

    /**
     *
     * @return
     * The constitution
     */
    public Integer getConstitution() {
        return constitution;
    }

    /**
     *
     * @param constitution
     * The constitution
     */
    public void setConstitution(Integer constitution) {
        this.constitution = constitution;
    }

    /**
     *
     * @return
     * The intelligence
     */
    public Integer getIntelligence() {
        return intelligence;
    }

    /**
     *
     * @param intelligence
     * The intelligence
     */
    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    /**
     *
     * @return
     * The wisdom
     */
    public Integer getWisdom() {
        return wisdom;
    }

    /**
     *
     * @param wisdom
     * The wisdom
     */
    public void setWisdom(Integer wisdom) {
        this.wisdom = wisdom;
    }

    /**
     *
     * @return
     * The charisma
     */
    public Integer getCharisma() {
        return charisma;
    }

    /**
     *
     * @param charisma
     * The charisma
     */
    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    /**
     *
     * @return
     * The wisdomSave
     */
    public Integer getWisdomSave() {
        return wisdomSave;
    }

    /**
     *
     * @param wisdomSave
     * The wisdom_save
     */
    public void setWisdomSave(Integer wisdomSave) {
        this.wisdomSave = wisdomSave;
    }

    /**
     *
     * @return
     * The damageVulnerabilities
     */
    public String getDamageVulnerabilities() {
        return damageVulnerabilities;
    }

    /**
     *
     * @param damageVulnerabilities
     * The damage_vulnerabilities
     */
    public void setDamageVulnerabilities(String damageVulnerabilities) {
        this.damageVulnerabilities = damageVulnerabilities;
    }

    /**
     *
     * @return
     * The damageResistances
     */
    public String getDamageResistances() {
        return damageResistances;
    }

    /**
     *
     * @param damageResistances
     * The damage_resistances
     */
    public void setDamageResistances(String damageResistances) {
        this.damageResistances = damageResistances;
    }

    /**
     *
     * @return
     * The damageImmunities
     */
    public String getDamageImmunities() {
        return damageImmunities;
    }

    /**
     *
     * @param damageImmunities
     * The damage_immunities
     */
    public void setDamageImmunities(String damageImmunities) {
        this.damageImmunities = damageImmunities;
    }

    /**
     *
     * @return
     * The conditionImmunities
     */
    public String getConditionImmunities() {
        return conditionImmunities;
    }

    /**
     *
     * @param conditionImmunities
     * The condition_immunities
     */
    public void setConditionImmunities(String conditionImmunities) {
        this.conditionImmunities = conditionImmunities;
    }

    /**
     *
     * @return
     * The senses
     */
    public String getSenses() {
        return senses;
    }

    /**
     *
     * @param senses
     * The senses
     */
    public void setSenses(String senses) {
        this.senses = senses;
    }

    /**
     *
     * @return
     * The languages
     */
    public String getLanguages() {
        return languages;
    }

    /**
     *
     * @param languages
     * The languages
     */
    public void setLanguages(String languages) {
        this.languages = languages;
    }

    /**
     *
     * @return
     * The challengeRating
     */
    public String getChallengeRating() {
        return challengeRating;
    }

    /**
     *
     * @param challengeRating
     * The challenge_rating
     */
    public void setChallengeRating(String challengeRating) {
        this.challengeRating = challengeRating;
    }

    /**
     *
     * @return
     * The specialAbilities
     */
    public RealmList<SpecialAbility> getSpecialAbilities() {
        return specialAbilities;
    }

    /**
     *
     * @param specialAbilities
     * The special_abilities
     */
    public void setSpecialAbilities(RealmList<SpecialAbility> specialAbilities) {
        this.specialAbilities = specialAbilities;
    }

    /**
     *
     * @return
     * The actions
     */
    public RealmList<Action> getActions() {
        return actions;
    }

    /**
     *
     * @param actions
     * The actions
     */
    public void setActions(RealmList<Action> actions) {
        this.actions = actions;
    }

}