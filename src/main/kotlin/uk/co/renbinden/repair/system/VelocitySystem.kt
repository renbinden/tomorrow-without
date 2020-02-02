package uk.co.renbinden.repair.system

import uk.co.renbinden.ilse.ecs.entity.Entity
import uk.co.renbinden.ilse.ecs.system.IteratingSystem
import uk.co.renbinden.repair.component.Acceleration
import uk.co.renbinden.repair.component.Position
import uk.co.renbinden.repair.component.Velocity


class VelocitySystem : IteratingSystem({
    it.has(Position)
            && it.has(Velocity)
}, priority = 4) {

    override fun processEntity(entity: Entity, dt: Double) {
        val velocity = entity[Velocity]
        val position = entity[Position]
        position.x += velocity.dx * dt
        position.y += velocity.dy * dt
    }

}