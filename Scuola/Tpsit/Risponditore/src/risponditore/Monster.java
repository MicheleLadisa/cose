/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risponditore;

import java.util.Random;

/**
 *
 * @author ladis
 */
class Monster
{
    private int hp;
    private int armor;
    public Monster()
    {
        hp=60 + new Random().nextInt(20) + 1;
        armor=3+new Random().nextInt(3)+1;
    }
    
    public int attack()
    {
        int damage=0;
        return damage;
    }
    
    public void damage(int damage) // sottrae dagli hp il valore di danno inserito
    {
        damage=damage-armor;
        hp=hp-damage;
    }
    
    public void damageArmor()
    {
        if(armor>0)
        {
            armor--;
        }
    }
}
