ServerEvents.recipes(event => {
  const toolTypes = [
    { type: 'sword',   base: 'advancednetherite:netherite_diamond_sword' },
    { type: 'pickaxe', base: 'advancednetherite:netherite_diamond_pickaxe' },
    { type: 'axe',     base: 'advancednetherite:netherite_diamond_axe' },
    { type: 'shovel',  base: 'advancednetherite:netherite_diamond_shovel' },
    { type: 'hoe',     base: 'advancednetherite:netherite_diamond_hoe' }
  ]

  toolTypes.forEach(tool => {
    // 1. Remove original recipe
    event.remove({ output: `deeperdarker:warden_${tool.type}` })

    // 2. Add Smithing Recipe
    event.smithing(
      `deeperdarker:warden_${tool.type}`,
      'deeperdarker:warden_upgrade_smithing_template',
      tool.base,
      'deeperdarker:reinforced_echo_shard'
    )
  })
})
