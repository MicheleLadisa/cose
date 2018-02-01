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
class Pg
{

    private int hp;
    private final int MAXHP = 40 + new Random().nextInt(20) + 1;
    private int mp;
    private final int MAXMP = 20 + new Random().nextInt(5) + 1;

    public Pg()
    {
        hp = MAXHP;
        mp = MAXMP;
    }

    public int attack()
    {
        int damage = 0;
        return damage;
    }

    public void damage(int damage) // sottrae dagli hp il valore di danno inserito
    {
        hp = hp - damage;
    }

    public void heal(int heal)
    {
        hp = hp + heal;
        if (hp > MAXHP)
        {
            hp = MAXHP;
        }
    }

    public void restorMp(int mpRestored)
    {
        mp = mp + mpRestored;
        if (mp > MAXMP)
        {
            mp = MAXMP;
        }
    }

    public int getHp()
    {
        return hp;
    }

    public int getMp()
    {
        return mp;
    }
}
