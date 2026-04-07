window.DOCS_DATA = {
  groups: [
    {
      title: 'Overview',
      pages: [
        { id: 'index', label: 'XPBottle Overview' },
        { id: 'installation', label: 'Installation' },
        { id: 'configuration', label: 'Configuration' }
      ]
    },
    {
      title: 'Usage',
      pages: [
        { id: 'commands', label: 'Commands' },
        { id: 'permissions', label: 'Permissions' },
        { id: 'gui-guide', label: 'GUI Guide' },
        { id: 'xp-bottles', label: 'XP Bottles' },
        { id: 'placeholders', label: 'Placeholders' },
        { id: 'troubleshooting', label: 'Troubleshooting' }
      ]
    },
    {
      title: 'Advanced',
      pages: [
        { id: 'developer-notes', label: 'Developer Notes' },
        { id: 'faq', label: 'FAQ' },
        { id: 'changelog', label: 'Changelog' }
      ]
    }
  ],
  pages: {
    index: {
      title: 'XPBottle Docs',
      topbarTitle: 'XPBottle Overview',
      meta: ['Overview', 'Home'],
      content: `
        <h1>XPBottle Documentation</h1>
        <p class="lead">XPBottle by EzInnovations lets players withdraw exact raw XP into custom bottles and redeem the exact amount later.</p>

        <section id="features"><h2>Feature Summary</h2>
          <ul>
            <li>Exact raw XP withdraw and redeem (not vanilla random XP).</li>
            <li>Configurable <span class="inline-code">/xpbottle</span> GUI with presets, info panel, withdraw-all, and close button.</li>
            <li>Persistent metadata validation for plugin-owned bottles.</li>
            <li>Redeem from main hand or offhand with optional shift-right-click redeem-all.</li>
            <li>Admin give + reload commands and optional PlaceholderAPI integration.</li>
          </ul>
        </section>

        <section id="requirements"><h2>Requirements</h2>
          <div class="table-wrap"><table>
            <thead><tr><th>Requirement</th><th>Value</th></tr></thead>
            <tbody>
              <tr><td>Java</td><td>21+</td></tr>
              <tr><td>Server</td><td>Paper or Folia</td></tr>
              <tr><td>Main Command</td><td><span class="inline-code">/xpbottle</span> (alias: <span class="inline-code">/xpb</span>)</td></tr>
              <tr><td>Optional Dependency</td><td>PlaceholderAPI</td></tr>
            </tbody>
          </table></div>
        </section>

        <section id="quick-links"><h2>Documentation Navigation</h2>
          <ul>
            <li>Installation and first boot setup.</li>
            <li>Commands and permissions reference.</li>
            <li>Configuration file breakdown for <span class="inline-code">config.yml</span>, <span class="inline-code">gui.yml</span>, <span class="inline-code">item.yml</span>, and <span class="inline-code">messages.yml</span>.</li>
            <li>GUI behavior, bottle validation, placeholder details, and troubleshooting.</li>
          </ul>
        </section>
      `
    },

    installation: {
      title: 'Installation · XPBottle Docs',
      topbarTitle: 'Installation',
      meta: ['Overview', 'Getting Started'],
      content: `
        <h1>Installation</h1>
        <p class="lead">Install XPBottle by placing the plugin jar in your plugins folder, then verify generated files and permissions on first boot.</p>

        <section id="requirements"><h2>Requirements</h2>
          <ul>
            <li>Java 21+ runtime.</li>
            <li>Paper or Folia server.</li>
            <li><span class="inline-code">XPBottle-&lt;version&gt;.jar</span>.</li>
            <li>(Optional) PlaceholderAPI for <span class="inline-code">%xpbottle_total_xp%</span> and <span class="inline-code">%xpbottle_level%</span>.</li>
          </ul>
        </section>

        <section id="steps"><h2>Installation Steps</h2>
          <ol>
            <li>Stop the server.</li>
            <li>Place <span class="inline-code">XPBottle-&lt;version&gt;.jar</span> in <span class="inline-code">/plugins</span>.</li>
            <li>Start once to generate config files in <span class="inline-code">plugins/XPBottle/</span>.</li>
            <li>Edit config values as needed and run <span class="inline-code">/xpbottle reload</span>.</li>
            <li>Test with <span class="inline-code">/xpbottle</span> and <span class="inline-code">/xpbottle withdraw 100</span>.</li>
          </ol>
        </section>

        <section id="generated-files"><h2>Generated Files</h2>
          <ul>
            <li><span class="inline-code">config.yml</span> for core behavior.</li>
            <li><span class="inline-code">gui.yml</span> for GUI layout and buttons.</li>
            <li><span class="inline-code">item.yml</span> for custom bottle appearance.</li>
            <li><span class="inline-code">messages.yml</span> for help and chat output.</li>
          </ul>
        </section>
      `
    },

    configuration: {
      title: 'Configuration · XPBottle Docs',
      topbarTitle: 'Configuration',
      meta: ['Overview', 'Configuration'],
      content: `
        <h1>Configuration Overview</h1>
        <p class="lead">XPBottle uses four YAML files in <span class="inline-code">plugins/XPBottle/</span>. Reload all files with <span class="inline-code">/xpbottle reload</span>.</p>

        <section id="config-yml"><h2>config.yml</h2>
          <p>Controls GUI refresh behavior, redeem-all toggles, and withdraw/redeem sounds.</p>
          <div class="code-block"><pre>gui:
  refresh-on-withdraw: true

redeem:
  shift-right-click-redeem-all: true

sounds:
  enabled: true
  withdraw: ENTITY_EXPERIENCE_ORB_PICKUP
  redeem: ENTITY_PLAYER_LEVELUP</pre></div>
        </section>

        <section id="gui-yml"><h2>gui.yml</h2>
          <p>Controls inventory title/size, filler/info items, withdraw-all behavior, and preset withdraw buttons.</p>
          <div class="code-block"><pre>gui:
  size: 27
  withdraw-all:
    mode: single_bottle
    slot: 22
  preset-amounts:
    amount-100:
      slot: 14
      amount: 100</pre></div>
        </section>

        <section id="item-messages"><h2>item.yml &amp; messages.yml</h2>
          <ul>
            <li><span class="inline-code">item.yml</span>: material, name/lore, glow, custom-model-data.</li>
            <li><span class="inline-code">messages.yml</span>: no-permission, invalid amount, success lines, and help text.</li>
            <li>Both files can be customized/localized safely for your network style.</li>
          </ul>
        </section>
      `
    },

    commands: {
      title: 'Commands · XPBottle Docs',
      topbarTitle: 'Commands',
      meta: ['Usage', 'Commands'],
      content: `
        <h1>Commands</h1>

        <section id="command-reference"><h2>Command Reference</h2>
          <div class="table-wrap"><table>
            <thead><tr><th>Command</th><th>Description</th><th>Permission</th></tr></thead>
            <tbody>
              <tr><td><strong>/xpbottle</strong></td><td>Opens the XPBottle GUI (players only).</td><td><span class="inline-code">xpbottle.use</span></td></tr>
              <tr><td><strong>/xpbottle help</strong></td><td>Shows help lines from <span class="inline-code">messages.yml</span>.</td><td>None enforced in code</td></tr>
              <tr><td><strong>/xpbottle withdraw &lt;amount&gt;</strong></td><td>Withdraws exact raw XP into one bottle.</td><td><span class="inline-code">xpbottle.withdraw</span></td></tr>
              <tr><td><strong>/xpbottle give &lt;player&gt; &lt;amount&gt;</strong></td><td>Gives target one exact XP bottle.</td><td><span class="inline-code">xpbottle.give</span></td></tr>
              <tr><td><strong>/xpbottle reload</strong></td><td>Reloads all XPBottle config files.</td><td><span class="inline-code">xpbottle.reload</span></td></tr>
            </tbody>
          </table></div>
        </section>

        <section id="usage-notes"><h2>Usage Notes</h2>
          <ul>
            <li>Main alias: <span class="inline-code">/xpb</span>.</li>
            <li><span class="inline-code">withdraw</span> and <span class="inline-code">give</span> require positive integer amounts.</li>
            <li>Unknown subcommands return the configured unknown-subcommand message.</li>
          </ul>
        </section>
      `
    },

    permissions: {
      title: 'Permissions · XPBottle Docs',
      topbarTitle: 'Permissions',
      meta: ['Usage', 'Permissions'],
      content: `
        <h1>Permissions</h1>

        <section id="permission-table"><h2>Permission Nodes</h2>
          <div class="table-wrap"><table>
            <thead><tr><th>Node</th><th>Default</th><th>Used For</th></tr></thead>
            <tbody>
              <tr><td><span class="inline-code">xpbottle.use</span></td><td><span class="inline-code">true</span></td><td>Open GUI with <span class="inline-code">/xpbottle</span>.</td></tr>
              <tr><td><span class="inline-code">xpbottle.withdraw</span></td><td><span class="inline-code">true</span></td><td>Use <span class="inline-code">/xpbottle withdraw</span>.</td></tr>
              <tr><td><span class="inline-code">xpbottle.reload</span></td><td><span class="inline-code">op</span></td><td>Use <span class="inline-code">/xpbottle reload</span>.</td></tr>
              <tr><td><span class="inline-code">xpbottle.give</span></td><td><span class="inline-code">op</span></td><td>Use <span class="inline-code">/xpbottle give</span>.</td></tr>
              <tr><td><span class="inline-code">xpbottle.redeem</span></td><td><span class="inline-code">true</span></td><td>Redeem plugin bottles by right-click.</td></tr>
            </tbody>
          </table></div>
        </section>

        <section id="notes"><h2>Notes</h2>
          <ul>
            <li><span class="inline-code">help</span> has no dedicated permission node.</li>
            <li>Tab completion for <span class="inline-code">withdraw</span>, <span class="inline-code">give</span>, and <span class="inline-code">reload</span> is permission-gated.</li>
          </ul>
        </section>
      `
    },

    'gui-guide': {
      title: 'GUI Guide · XPBottle Docs',
      topbarTitle: 'GUI Guide',
      meta: ['Usage', 'GUI'],
      content: `
        <h1>GUI Guide</h1>

        <section id="open"><h2>How It Opens</h2>
          <ul>
            <li>Command: <span class="inline-code">/xpbottle</span>.</li>
            <li>Requires: <span class="inline-code">xpbottle.use</span>.</li>
            <li>Players only.</li>
          </ul>
        </section>

        <section id="elements"><h2>Main GUI Elements</h2>
          <ol>
            <li><strong>Preset buttons</strong>: from <span class="inline-code">gui.preset-amounts</span>, each with slot/material/name/lore/amount.</li>
            <li><strong>Info item</strong>: supports <span class="inline-code">%total_xp%</span> and <span class="inline-code">%level%</span>.</li>
            <li><strong>Withdraw-all</strong>: mode <span class="inline-code">disabled</span> or <span class="inline-code">single_bottle</span>.</li>
            <li><strong>Close button</strong>: configured slot and styling from <span class="inline-code">gui.close-button</span>.</li>
          </ol>
        </section>

        <section id="behavior"><h2>Layout and Safety Behavior</h2>
          <ul>
            <li>GUI size is sanitized to a valid multiple of 9 between 9 and 54.</li>
            <li>Invalid slots or non-positive preset values are skipped.</li>
            <li>Top inventory clicks/drags are cancelled to prevent moving GUI items.</li>
          </ul>
        </section>
      `
    },

    'xp-bottles': {
      title: 'XP Bottles · XPBottle Docs',
      topbarTitle: 'XP Bottles',
      meta: ['Usage', 'Feature'],
      content: `
        <h1>XP Bottles</h1>

        <section id="storage"><h2>How XP Is Stored</h2>
          <p>XPBottle custom items store metadata in <span class="inline-code">PersistentDataContainer</span> including:</p>
          <ul>
            <li><span class="inline-code">is_xpbottle</span> marker key.</li>
            <li><span class="inline-code">stored_xp</span> exact integer value.</li>
            <li><span class="inline-code">item_version</span> (current version: 1).</li>
          </ul>
        </section>

        <section id="withdraw-redeem"><h2>Withdraw and Redeem Behavior</h2>
          <ol>
            <li>Withdraw removes exact requested XP and creates one matching custom bottle.</li>
            <li>If inventory is full, bottle drops at player location.</li>
            <li>Redeem uses the clicked hand (main/offhand) and restores exact XP.</li>
            <li>Optional shift-right-click can redeem the full in-hand stack.</li>
          </ol>
        </section>

        <section id="validation"><h2>Validation and Protection</h2>
          <ul>
            <li>Invalid/corrupt marked bottles are blocked from granting XP.</li>
            <li>Invalid bottle data may consume one item safely to reduce exploit loops.</li>
            <li>A short per-player per-hand cooldown helps prevent accidental double-redeems.</li>
          </ul>
        </section>
      `
    },

    placeholders: {
      title: 'Placeholders · XPBottle Docs',
      topbarTitle: 'Placeholders',
      meta: ['Usage', 'Integrations'],
      content: `
        <h1>PlaceholderAPI Integration</h1>

        <section id="behavior"><h2>Dependency Behavior</h2>
          <ul>
            <li>PlaceholderAPI is a soft dependency.</li>
            <li>XPBottle registers placeholders only when PlaceholderAPI is installed and enabled.</li>
            <li>If PlaceholderAPI is missing, XPBottle still functions without placeholders.</li>
          </ul>
        </section>

        <section id="registered"><h2>Registered Placeholders</h2>
          <div class="table-wrap"><table>
            <thead><tr><th>Placeholder</th><th>Returns</th></tr></thead>
            <tbody>
              <tr><td><span class="inline-code">%xpbottle_total_xp%</span></td><td>Player total raw XP points.</td></tr>
              <tr><td><span class="inline-code">%xpbottle_level%</span></td><td>Player Minecraft level.</td></tr>
            </tbody>
          </table></div>
        </section>

        <section id="runtime"><h2>Runtime Conditions</h2>
          <ul>
            <li>Offline/null player requests return <span class="inline-code">0</span>.</li>
            <li>Unknown placeholder parameters return <span class="inline-code">null</span> to PlaceholderAPI chain.</li>
          </ul>
        </section>
      `
    },

    troubleshooting: {
      title: 'Troubleshooting · XPBottle Docs',
      topbarTitle: 'Troubleshooting',
      meta: ['Usage', 'Support'],
      content: `
        <h1>Troubleshooting</h1>

        <section id="command-failures"><h2>Common Command Failures</h2>
          <ul>
            <li><strong>Unknown subcommand</strong>: command did not match <span class="inline-code">help|reload|withdraw|give</span>.</li>
            <li><strong>invalid-amount</strong>: amount is non-numeric or less than/equal to 0.</li>
            <li><strong>not-enough-xp</strong>: player has less total raw XP than requested.</li>
            <li><strong>player-only</strong>: console attempted a player-only flow.</li>
          </ul>
        </section>

        <section id="redeem-issues"><h2>Redeem Issues</h2>
          <ul>
            <li>Invalid bottle metadata prevents XP grant.</li>
            <li>Invalid plugin-marked bottles may be consumed and report invalid data message.</li>
            <li>A short internal cooldown (~75ms) can block duplicate redeem spam.</li>
          </ul>
        </section>

        <section id="quick-checks"><h2>Quick Checks</h2>
          <ul>
            <li>Verify sound keys match Bukkit <span class="inline-code">Sound</span> enum names.</li>
            <li>Check <span class="inline-code">gui.size</span> and slot indexes in <span class="inline-code">gui.yml</span>.</li>
            <li>Run <span class="inline-code">/xpbottle reload</span> after YAML edits.</li>
          </ul>
        </section>
      `
    },

    'developer-notes': {
      title: 'Developer Notes · XPBottle Docs',
      topbarTitle: 'Developer Notes',
      meta: ['Advanced', 'Contributors'],
      content: `
        <h1>Developer Notes</h1>

        <section id="entrypoint"><h2>Package and Entry Point</h2>
          <ul>
            <li>Base package: <span class="inline-code">com.ezinnovations.xpbottle</span>.</li>
            <li>Main class: <span class="inline-code">com.ezinnovations.xpbottle.XPBottlePlugin</span>.</li>
          </ul>
        </section>

        <section id="systems"><h2>Key Systems</h2>
          <ul>
            <li><strong>XPBottleCommand</strong> for command parsing and execution.</li>
            <li><strong>XPBottleGuiManager + XPBottleGuiListener</strong> for config-driven GUI handling.</li>
            <li><strong>XPBottleItemManager</strong> for create/validate/withdraw/give/redeem bottle lifecycle.</li>
            <li><strong>PlayerRedeemListener</strong> for right-click redeem logic and cooldown guard.</li>
            <li><strong>XPBottlePlaceholderExpansion</strong> for PlaceholderAPI integration.</li>
          </ul>
        </section>

        <section id="reload"><h2>Reload Behavior</h2>
          <p><span class="inline-code">/xpbottle reload</span> calls <span class="inline-code">XPBottlePlugin#reloadPlugin()</span> to reload <span class="inline-code">config.yml</span>, <span class="inline-code">gui.yml</span>, <span class="inline-code">item.yml</span>, and <span class="inline-code">messages.yml</span>.</p>
        </section>
      `
    },

    faq: {
      title: 'FAQ · XPBottle Docs',
      topbarTitle: 'FAQ',
      meta: ['Advanced', 'Support'],
      content: `
        <h1>FAQ</h1>

        <section id="menu"><h2>Why does /xpbottle not open a menu?</h2>
          <ul>
            <li>Ensure player has <span class="inline-code">xpbottle.use</span>.</li>
            <li>Confirm the command is run by a player (not console).</li>
            <li>Check startup logs for plugin load errors.</li>
          </ul>
        </section>

        <section id="redeem"><h2>Why can players withdraw but not redeem?</h2>
          <ul>
            <li>Ensure <span class="inline-code">xpbottle.redeem</span> is granted.</li>
            <li>Verify they are right-clicking XPBottle custom items, not vanilla bottles.</li>
          </ul>
        </section>

        <section id="placeholder"><h2>Is PlaceholderAPI required?</h2>
          <p>No. PlaceholderAPI is optional and only needed for <span class="inline-code">%xpbottle_*%</span> placeholders.</p>
        </section>
      `
    },

    changelog: {
      title: 'Changelog · XPBottle Docs',
      topbarTitle: 'Changelog',
      meta: ['Advanced', 'Releases'],
      content: `
        <h1>Changelog</h1>

        <section id="unreleased"><h2>[Unreleased]</h2>
          <h3>Added</h3>
          <ul><li>Add new entries here.</li></ul>
          <h3>Changed</h3>
          <ul><li>Add changed behavior here.</li></ul>
          <h3>Fixed</h3>
          <ul><li>Add bug fixes here.</li></ul>
          <h3>Removed</h3>
          <ul><li>Add removed/deprecated items here.</li></ul>
        </section>

        <section id="v100"><h2>[1.0.0] - 2026-04-07</h2>
          <ul>
            <li>Initial public documentation set in GitBook structure.</li>
          </ul>
        </section>
      `
    }
  }
};
