"""Expose Gradle errors and failing tests in CI annotations when log download is unavailable."""
import sys
from pathlib import Path
import xml.etree.ElementTree as ET


def error(message):
    escaped = message.replace('%', '%25').replace('\r', '%0D').replace('\n', '%0A')
    print('::error::' + escaped)


log = Path(sys.argv[1])
if log.exists():
    lines = log.read_text(errors='replace').splitlines()
    for line in lines:
        if line.startswith('e: ') or ' FAILED' in line or line.startswith('> '):
            error(line[:1500])
    if '* What went wrong:' in lines:
        i = lines.index('* What went wrong:')
        error('\n'.join(lines[i:i + 15]))
for report in Path('app/build/test-results').rglob('TEST-*.xml'):
    tree = ET.parse(report)
    for test in tree.findall('.//testcase'):
        for failure in list(test.findall('failure')) + list(test.findall('error')):
            error(f"{test.get('classname')}.{test.get('name')}: {failure.get('message')}\n{(failure.text or '')[:1500]}")
