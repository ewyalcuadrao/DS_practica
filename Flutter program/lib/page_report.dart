import 'package:flutter/material.dart';
import 'package:codelab_timetracker/page_activities.dart';
import 'package:intl/intl.dart';

class PageReport extends StatefulWidget {
  const PageReport({Key? key}) : super(key: key);

  @override
  _PageReportState createState() => _PageReportState();
}

class _PageReportState extends State<PageReport> {

  DateTime getToday() {
    return DateTime.now();
  }

  DateTime getYesterday() {
    return getToday().subtract(const Duration(days: 1));
  }

  DateTime getMondayThisWeek() {
    return DateTime(getToday().year, getToday().month,
        getToday().day - getToday().weekday + 1);
  }


  DateTime getSundayLastWeek() {
    return getMondayThisWeek().subtract(new Duration(days: 1));
  }

  DateTime getMondayLastWeek() {
    return getMondayThisWeek().subtract(new Duration(days: 7));
  }
  
  DateTime From = DateTime.now();
  DateTime To = DateTime.now().add(new Duration(days: 5));

  DateTimeRange? TimeRange = DateTimeRange(start: DateTime.now(), end: DateTime.now().add(new Duration(days: 5)));

  DateFormat Date_format = DateFormat('yyyy-MM-dd');

  String getFromString(){
    return Date_format.format(From);
  }

  String getToString(){
    return Date_format.format(To);
  }
  
  String? dropdownvalue_P = 'This week';
  var items_periods = ['This week', 'Last week', 'Yesterday', 'Today', 'Other'];

  String? dropdownvalue_C = 'Brief';
  var items_content = ['Brief', 'Detailed', 'Statistics'];

  String? dropdownvalue_F = 'Web page';
  var items_format = ['Web page', 'PDF', 'Text'];

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Report'),
        actions: <Widget>[
          IconButton(icon: const Icon(Icons.home),
            onPressed: () {
              Navigator.of(context)
                  .push(MaterialPageRoute<void>(
                builder: (context) => PageActivities(),
              ));
            },
          ),
        ],
      ),
      body: Container(
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.all(15.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Padding(
                      padding: EdgeInsets.all(20.0),
                      child: Text('Period'),
                    ),
                    DropdownButton(
                      value: dropdownvalue_P = 'This week',
                      items: items_periods.map((String items) {
                        return DropdownMenuItem(
                            value: items,
                            child: Text(items)
                        );
                      }).toList(),
                      onChanged: (String? newValue) {
                        setState(() {
                          dropdownvalue_P = newValue;
                        });
                      },
                    ),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(15.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Padding(
                      padding: EdgeInsets.all(20.0),
                      child: Text('From'),
                    ),
                    Padding(
                      padding: const EdgeInsets.all(20.0),
                      child: Text(getFromString()),
                    ),
                    IconButton(icon: const Icon(Icons.date_range),
                      onPressed: () {
                        _pickFromDate();
                      },
                    ),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(15.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Padding(
                      padding: EdgeInsets.all(20.0),
                      child: Text('To'),
                    ),
                     Padding(
                      padding: const EdgeInsets.all(20.0),
                      child: Text(getToString()),
                    ),
                    IconButton(icon: const Icon(Icons.date_range),
                      onPressed: () {
                        _pickToDate();
                      },
                    ),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(15.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Padding(
                      padding: EdgeInsets.all(20.0),
                      child: Text('Content'),
                    ),
                    DropdownButton(
                      value: dropdownvalue_C = 'Brief',
                      items: items_content.map((String items) {
                        return DropdownMenuItem(
                            value: items,
                            child: Text(items)
                        );
                      }).toList(),
                      onChanged: (String? newValue) {
                        setState(() {
                          dropdownvalue_C = newValue;
                        });
                      },
                    ),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(15.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    const Padding(
                      padding: EdgeInsets.all(20.0),
                      child: Text('Content'),
                    ),
                    DropdownButton(
                      value: dropdownvalue_F = 'Web page',
                      items: items_format.map((String items) {
                        return DropdownMenuItem(
                            value: items,
                            child: Text(items)
                        );
                      }).toList(),
                      onChanged: (String? newValue) {
                        setState(() {
                          dropdownvalue_F = newValue;
                        });
                      },
                    ),
                  ],
                ),
              ),
              Padding(
                padding: const EdgeInsets.all(15.0),
                child: Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    TextButton(
                      child: const Text('Generate',
                        style: TextStyle(fontSize: 20),
                      ),
                      onPressed: () {
                        Navigator.of(context).pop();
                      },
                    ),
                  ],
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }

  _pickFromDate() async {
    DateTime? newStart = await showDatePicker(
      context: context,
      firstDate: DateTime(getToday().year - 5),
      lastDate: DateTime(getToday().year + 5),
      initialDate: getToday(),
    );
    From = newStart!;
    DateTime end = To;
    if (end.difference(newStart!) >= const Duration(days: 0)) {
    TimeRange = DateTimeRange(start: newStart, end: end);
// x is where you store the (From,To) DateTime pairs
// associated to the ’Other’ option
    setState(() {
      dropdownvalue_P = 'Other'; // to redraw the screen
    });
    } else {
    _showAlertDates();
    }
  }

  _pickToDate() async {
    DateTime? newEnd = await showDatePicker(
      context: context,
      firstDate: DateTime(getToday().year - 5),
      lastDate: DateTime(getToday().year + 5),
      initialDate: getToday(),
    );
    To = newEnd!;
    DateTime start = From;
    if (newEnd.difference(start) >= const Duration(days: 0)) {
    TimeRange = DateTimeRange(start: start, end: newEnd);
// x is where you store the (From,To) DateTime pairs
// associated to the ’Other’ option
    setState(() {
      dropdownvalue_P = 'Other'; // to redraw the screen
    });
    } else {
    _showAlertDates();
    }
  }

  _showAlertDates() async {
    return showDialog<void>(
      context: context,
      barrierDismissible: false, // user must tap button!
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text('Range Dates'),
          content: SingleChildScrollView(
            child: ListBody(
              children: const <Widget>[
                Text('The From date is after the To Date.'),
                Text(''),
                Text('Please, select a new date'),
              ],
            ),
          ),
          actions: <Widget>[
            TextButton(
              child: const Text('Accept',
                style: TextStyle(fontSize: 20),
              ),
              onPressed: () {
                Navigator.of(context).pop();
              },
            ),
          ],
        );
      },
    );
  }
}