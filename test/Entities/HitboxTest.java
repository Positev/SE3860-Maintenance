package Entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HitboxTest {

    private Hitbox hitBox1;
    @BeforeEach
    void setUp() {
        hitBox1=new Hitbox(500, 500, 200, 300);
    }

    @Test
    void testIsTopCollisionFalse() {
        Hitbox hitBox2=new Hitbox(100,805,100,50);
        assertFalse(hitBox1.detectCollision(hitBox2));
    }

    @Test
    void testIsBottomCollisionFalse() {
        Hitbox hitBox2=new Hitbox(100,195,100,50);
        assertFalse(hitBox1.detectCollision(hitBox2));
    }

    @Test
    void testIsMiddleLeftCollisionFalse() {
        Hitbox hitBox2=new Hitbox(295,400,100,50);
        assertFalse(hitBox1.detectCollision(hitBox2));
    }

    @Test
    void testIsBottomLeftCollisionFalse() {
        Hitbox hitBox2=new Hitbox(705,400,100,50);
        assertFalse(hitBox1.detectCollision(hitBox2));
    }

    @Test
    void testIsCollisionTrue() {
        Hitbox hitBox2=new Hitbox(500,500,100,50);
        assertTrue(hitBox1.detectCollision(hitBox2));
    }
}