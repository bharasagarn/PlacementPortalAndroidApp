import 'package:flutter/material.dart';
import 'package:placementportal/company_login.dart';
import 'package:placementportal/student_login.dart';

void main() => runApp(LaunchScreen());

class LaunchScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      initialRoute: '/',
      routes: {
        '/studloginroute': (context) => StudentLoginScreen(),
        '/comploginroute': (context) => CompanyLoginScreen(),
      },

      title: 'PlacementPortalApp',
      home: Scaffold(
        appBar: AppBar(
          title: Text('PlacementPortal Launch'),
        ),
        body: LaunchWidget(),
      ),
    );
  }
}

class LaunchWidget extends StatelessWidget {
  LaunchWidget({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          const Text('Who are you?'),
          const SizedBox(height: 30),
          RaisedButton(
            onPressed: () {
              Navigator.pushNamed(context, '/studloginroute');
            },
            child: const Text('Student', style: TextStyle(fontSize: 20)),
          ),
          const SizedBox(height: 30),
          RaisedButton(
            onPressed: () {
              Navigator.pushNamed(context, '/comploginroute');
            },
            child: const Text('Company', style: TextStyle(fontSize: 20)),
          ),
        ],
      ),
    );
  }
}