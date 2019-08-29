<?php
        
    $filetxt = 'data.txt';

        // check if all form data are submited, else output error message
        if(isset($_POST['element_1_1']) && isset($_POST['element_2']) && isset($_POST['element_3_1']) && isset($_POST['element_3_2']) && isset($_POST['element_3_1'])) {
        // if form fields are empty, outputs message, else, gets their data
        if(empty($_POST['element_1_1']) || empty($_POST['element_2']) || empty($_POST['element_3_1']) || empty($_POST['element_3_2']) || empty($_POST['element_3_3'])) {
            echo 'All fields are required';
        }
        else {
        // gets and adds form data into an array
        $data = array(
          'Name'=> $_POST['element_1_1'],
          'Email'=> $_POST['element_2'],
          'Phn1'=> $_POST['element_3_1'],
          'Phn2'=> $_POST['element_3_2'],
          'Phn3'=> $_POST['element_3_3'],
	  'Roll No'=> $_POST['element_4'],
          'Month'=> $_POST['element_5_1'],
          'Date'=> $_POST['element_5_2'],
          'Year'=> $_POST['element_5_3'],
	  'Inst Name'=> $_POST['element_6'],
	  'Inst IP'=> $_POST['element_7'],
	  'Domain'=> $_POST['element_8'],
	  'Lab Name'=> $_POST['element_9'],		
        );

        // path and name of the file
        $filetxt = 'data.txt';

        $arr_data = array();        // to store all form data

        // check if the file exists
        if(file_exists($filetxt)) {
          // gets json-data from file
          $jsondata = file_get_contents($filetxt);

          // converts json string into array
          $arr_data = json_decode($jsondata, true);
        }

        // appends the array with new form data
        $arr_data[] = $data;

        // encodes the array into a string in JSON format (JSON_PRETTY_PRINT - uses whitespace in json-string, for human readable)
        $jsondata = json_encode($arr_data, JSON_PRETTY_PRINT);

        // saves the json string in "data.txt" (in "dirdata" folder)
        // outputs error message if data cannot be saved
        if(file_put_contents('data.txt', $jsondata)) echo 'Data successfully saved';
        else echo 'Tidak dapat menyimpan data di "data.txt"';
      }
    }
        else echo 'Form fields not submited';
    ?>
