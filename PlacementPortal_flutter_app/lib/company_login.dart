import 'package:flutter/material.dart';

class CompanyLoginScreen extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Company Login"),
      ),
      body: CompanyLoginWidget(),
    );
  }
}

class CompanyLoginWidget extends StatelessWidget {
  CompanyLoginWidget({Key key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: <Widget>[
          const SizedBox(height: 30),
          RaisedButton(
            onPressed: () {
//
            },
            child: const Text('Login', style: TextStyle(fontSize: 20)),
          ),
        ],
      ),
    );
  }
}