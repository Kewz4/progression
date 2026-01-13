ServerEvents.recipes(event => {
  // Remove default Jellyfish Umbrella recipe
  event.remove({ output: 'bossesunleashed:jellyfish_umbrella' })

  // Add new recipe using Warden Sword
  // Assuming pattern similar to standard or defined in mod, but enforcing Warden Sword as core.
  // Standard umbrella might be sticks + material. Jellyfish umbrella implies Jellyfish parts.
  // I'll assume a standard shape for now, replacing the main stick/handle or blade with Warden Sword?
  // User said "warden_sword will be the sword used when crafting the jellyfish umbrella".
  // Usually this means it's an ingredient.
  // Let's use a shapeless or shaped recipe that matches the likely ingredients (Jellyfish drops + Warden Sword).
  // Ingredients from previous tasks: `bossesunleashed:celestial_jelly`.

  event.shaped(
    'bossesunleashed:jellyfish_umbrella',
    [
      'JJJ',
      'JSJ',
      ' J '
    ],
    {
      J: 'bossesunleashed:celestial_jelly',
      S: 'deeperdarker:warden_sword'
    }
  )
})
