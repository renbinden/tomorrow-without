package uk.co.renbinden.repair.system

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.repair.component.Acceleration
import uk.co.renbinden.repair.component.Velocity

class AccelerationSystem : IteratingSystem({
    it.has(Acceleration)
            && it.has(Velocity)
}, priority = 2) {

    override fun processEntity(entity: Entity, dt: Double) {
        val acceleration = entity[Acceleration]
        val velocity = entity[Velocity]
        velocity.dx += acceleration.ddx * dt
        velocity.dy += acceleration.ddy * dt
    }

}