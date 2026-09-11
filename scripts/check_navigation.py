"""Targeted source checks for the home-header navigation regression (no Android SDK needed)."""
from pathlib import Path
import re
import unittest

ROOT = Path(__file__).resolve().parents[1]
SRC = ROOT / 'app/src/main/kotlin/com/nikhil/yt'


class HomeNavigationRegressionTest(unittest.TestCase):
    def test_removed_controls_stay_removed(self):
        header = (SRC / 'ui/component/TowfiiHeader.kt').read_text()
        for obsolete in ('onSearch', 'towfik_search_hint', 'towfik_hero_title', 'Canvas('):
            self.assertNotIn(obsolete, header)

    def test_no_navigation_to_bare_search(self):
        # Search is activity UI, not a NavHost destination. Results require a query.
        for path in SRC.rglob('*.kt'):
            self.assertIsNone(re.search(r'\.navigate\(\s*(?:Screens\.Search\.route|"search")\s*\)', path.read_text()), str(path))

    def test_home_shortcuts_exist_in_graph(self):
        home = (SRC / 'ui/screens/HomeScreen.kt').read_text()
        graph = (SRC / 'ui/screens/NavigationBuilder.kt').read_text()
        screens = (SRC / 'ui/screens/Screens.kt').read_text()
        for screen in re.findall(r'navController\.navigate\(Screens\.(\w+)\.route\)', home):
            match = re.search(r'object ' + screen + r'\s*:\s*Screens\([\s\S]*?route\s*=\s*"([^"]+)"', screens)
            self.assertIsNotNone(match, screen)
            self.assertRegex(graph, r'composable\(\s*(?:Screens\.' + screen + r'\.route|"' + re.escape(match[1]) + r'")')

    def test_settings_card_destinations_exist(self):
        settings = (SRC / 'ui/screens/settings/VeluneSettingsScreen.kt').read_text()
        graph = (SRC / 'ui/screens/NavigationBuilder.kt').read_text()
        for route in re.findall(r'navController\.navigate\("([^"]+)"\)', settings):
            self.assertIn('composable("' + route + '")', graph)


if __name__ == '__main__':
    unittest.main()
