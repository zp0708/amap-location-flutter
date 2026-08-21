import 'package:flutter_test/flutter_test.dart';

import 'package:amap_location_flutter_plugin_example/main.dart';

void main() {
  testWidgets('App builds and shows the location buttons',
      (WidgetTester tester) async {
    await tester.pumpWidget(MyApp());

    expect(find.text('开始定位'), findsOneWidget);
    expect(find.text('停止定位'), findsOneWidget);
  });
}
