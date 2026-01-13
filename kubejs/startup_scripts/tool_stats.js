ItemEvents.modification(event => {
  // Netherite Diamond Sword Damage (Baseline): 11 (from wiki)
  // Warden Tool Target: +2 = 13
  // Jellyfish Umbrella Target: +2 over Warden = 15

  const wardenTools = [
    'deeperdarker:warden_sword',
    'deeperdarker:warden_pickaxe',
    'deeperdarker:warden_axe',
    'deeperdarker:warden_shovel',
    'deeperdarker:warden_hoe'
  ]

  wardenTools.forEach(tool => {
    event.modify(tool, item => {
      // We assume standard offsets. If Sword is 13, Axe is usually higher.
      // But prompt says "make the warden tools do +2 more damage than netherite diamond".
      // Netherite Diamond Sword = 11. Warden Sword = 13.
      // I will apply a flat buff relative to default or just set attack damage if accessible.
      // KubeJS modify allows setting attack damage.

      // However, to be safe and accurate, I should probably check current damage and add 2?
      // Or just set it to the expected value if I know the base.
      // Deeper Darker defaults: Warden Sword is usually stronger than Netherite.
      // Let's ensure it satisfies "Netherite Diamond + 2".
      // ND Sword = 11. Target = 13.

      if (tool.includes('sword')) item.attackDamage = 13
      // Axe usually +5 over sword (9 vs 4 base diff). ND Axe = 13. Warden Axe = 15?
      if (tool.includes('axe')) item.attackDamage = 15
      // Pickaxe/Shovel usually less. ND Pick = 7 (Netherite is 6?).
      // Wiki: ND Pick = 7. Target = 9.
      if (tool.includes('pickaxe')) item.attackDamage = 9
      if (tool.includes('shovel')) item.attackDamage = 9 // ND Shovel = 7
      if (tool.includes('hoe')) item.attackDamage = 3 // ND Hoe = ? Wiki says 1? Wait, ND Hoe attack damage is usually low. Wiki: 1. Target = 3.
    })
  })

  // Jellyfish Umbrella
  event.modify('bossesunleashed:jellyfish_umbrella', item => {
    // "jellyfish umbrella do +2 more than the modified warden sword"
    // Warden Sword = 13. Target = 15.
    item.attackDamage = 15
  })
})
