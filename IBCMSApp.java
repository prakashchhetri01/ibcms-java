import java.util.*;
import java.io.*;   //imports required for file operations

public class IBCMSApp { //main class for IBCMS app
    public static void main(String[] args) {
        System.out.println("=== IMMIGRATION AND BORDER CONTROL MANAGEMENT SYSTEM ===");
        System.out.println("Welcome to IBCMS APP");
        System.out.println("========================================================");

        Scanner user_input = new Scanner(System.in);
        boolean first_run = !new File("prakashGC_data.txt").exists();
        IBCMS management_system = new IBCMS(user_input); //create system instance

        if (first_run) {
            management_system.initializeWithSampleData();// add sample data if no records are created
        }

        int user_choice;

        do {
            displayMainMenu();
            while (!user_input.hasNextInt()) { //for conforming valid integer
                System.out.print("Please enter a valid number: ");
                user_input.next();
            }
            user_choice = user_input.nextInt();
            user_input.nextLine();

            switch (user_choice) {
                case 1:
                    management_system.viewAllRecords();
                    break;
                case 2:
                    addNewPersonRecord(management_system, user_input);
                    break;
                case 3:
                    System.out.print("Enter Person ID to update: ");
                    String update_id = user_input.nextLine();
                    management_system.updatePersonRecord(update_id);
                    break;
                case 4:
                    System.out.print("Enter Person ID to delete: ");
                    String delete_id = user_input.nextLine();
                    management_system.deletePersonRecord(delete_id);
                    break;
                case 5:
                    System.out.print("Enter Person ID to search: ");
                    String search_id = user_input.nextLine();
                    management_system.searchPersonRecord(search_id);
                    break;
                case 6:
                    System.out.print("Enter name or nationality to search: ");
                    String search_term = user_input.nextLine();
                    management_system.searchByNameOrNationality(search_term);
                    break;
                case 7:
                    management_system.displaySummaryData();
                    break;
                case 0:
                    System.out.println("Thank you for using IBCMS. Goodbye!");
                    management_system.shutdown(); // Graceful shutdown with auto-save
                    break;
                default:
                    System.out.println("Invalid choice! Please select a number between 0-7.");
            }

            if (user_choice != 0) {
                System.out.println("\nPress Enter to continue...");
                user_input.nextLine();
            }
        } while (user_choice != 0);

        user_input.close();
        System.out.println("IBCMS Application terminated successfully.");
    }

    private static void displayMainMenu() { //main menu display bar
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║            «IBCMS MAIN MENU»          ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║ 1. View All Records                   ║");
        System.out.println("║ 2. Add New Record                     ║");
        System.out.println("║ 3. Update Record                      ║");
        System.out.println("║ 4. Delete Record                      ║");
        System.out.println("║ 5. Search by ID                       ║");
        System.out.println("║ 6. Search by Name/Nationality         ║");
        System.out.println("║ 7. Display Summary Statistics         ║");
        System.out.println("║ 0. Exit Application                   ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("Note: Data is automatically saved after each change");
        System.out.print("Enter your choice (0-7): ");
    }

    private static void addNewPersonRecord(IBCMS system, Scanner input) {//prompt for adding new person record
        try {
            System.out.println("\n=== ADD NEW PERSON RECORD ===");
            System.out.println("Select Person Type:");
            System.out.println("1. Visitor");
            System.out.println("2. Immigrant");
            System.out.println("3. Asylum Seeker");
            System.out.print("Enter choice (1-3): ");

            int type_choice = input.nextInt();
            input.nextLine();

            System.out.print("Enter Person ID: ");
            String person_id = input.nextLine();
            System.out.print("Enter Full Name: ");
            String person_name = input.nextLine();
            System.out.print("Enter Age: ");
            int person_age = input.nextInt();
            input.nextLine();
            System.out.print("Enter Nationality: ");
            String person_nationality = input.nextLine();

            Person new_person = null;

            switch (type_choice) { //ask for more fields
                case 1:
                    System.out.print("Enter Visit Purpose: ");
                    String visit_purpose = input.nextLine();
                    System.out.print("Enter Visit Duration: ");
                    String visit_duration = input.nextLine();
                    new_person = new Visitor(person_id, person_name, person_age, person_nationality,
                            visit_purpose, visit_duration);
                    break;
                case 2:
                    System.out.print("Enter Visa Type: ");
                    String visa_type = input.nextLine();
                    new_person = new Immigrant(person_id, person_name, person_age, person_nationality,
                            visa_type);
                    break;
                case 3:
                    System.out.print("Enter Reason for Asylum: ");
                    String asylum_reason = input.nextLine();
                    System.out.print("Enter Application Status: ");
                    String application_status = input.nextLine();
                    new_person = new AsylumSeeker(person_id, person_name, person_age, person_nationality,
                            asylum_reason, application_status);
                    break;
                default:
                    System.out.println("Invalid person type selected!");
                    return;
            }

            if (new_person != null) {
                system.addPersonRecord(new_person);
                System.out.println("\nNew person record created:");
                new_person.displayPersonInfo();
            }
        } catch (InputMismatchException input_error) {
            System.out.println("Error: Invalid input format. Please enter correct data types.");
            input.nextLine();
        }
    }
}

abstract class Person { //abstract class includes general information shared by all persons
    private String person_id; //unique ID for person
    private String person_name; //full name of person
    private int person_age; //age of the person
    private String person_nationality; //nationality of the person

    //constructor to set up common field in the system
    public Person(String person_id, String person_name, int person_age, String person_nationality) {
        this.person_id = person_id;
        this.person_name = person_name;
        this.person_age = person_age;
        this.person_nationality = person_nationality;
    }

    //getter and setter for accessing and modifying private fields
    public String getPersonId() { return person_id; }
    public String getPersonName() { return person_name; }
    public int getPersonAge() { return person_age; }
    public String getPersonNationality() { return person_nationality; }

    public void setPersonId(String person_id) { this.person_id = person_id; }
    public void setPersonName(String person_name) { this.person_name = person_name; }
    public void setPersonAge(int person_age) { this.person_age = person_age; }
    public void setPersonNationality(String person_nationality) { this.person_nationality = person_nationality; }

    public abstract void displayPersonInfo();//abstract method for subclasses implementation

    public void displayBasicInfo() { //displays basic information to persons
        System.out.println("Person ID: " + person_id);
        System.out.println("Name: " + person_name);
        System.out.println("Age: " + person_age);
        System.out.println("Nationality: " + person_nationality);
    }

    public abstract String getPersonType();//abstract method for person type
    public abstract String getSpecificInfo();
}

class Visitor extends Person { //subclass visitor
    private String visit_purpose; //reason for visiting country
    private String visit_duration;// staying duration for visitor

    public Visitor(String person_id, String person_name, int person_age, String person_nationality,
                   String visit_purpose, String visit_duration) {
        super(person_id, person_name, person_age, person_nationality);
        this.visit_purpose = visit_purpose;
        this.visit_duration = visit_duration;
    }

    // getter and setter for visitor sub class
    public String getVisitPurpose() { return visit_purpose; }
    public void setVisitPurpose(String visit_purpose) { this.visit_purpose = visit_purpose; }
    public String getVisitDuration() { return visit_duration; }
    public void setVisitDuration(String visit_duration) { this.visit_duration = visit_duration; }

    @Override // display visitor information
    public void displayPersonInfo() {
        displayBasicInfo();
        System.out.println("Person Type: Visitor");
        System.out.println("Visit Purpose: " + visit_purpose);
        System.out.println("Visit Duration: " + visit_duration);
    }

    @Override
    public String getPersonType() {
        return "Visitor";
    }

    @Override
    public String getSpecificInfo() {
        return visit_purpose + "," + visit_duration;
    }
}

class Immigrant extends Person { //immigrant subclass
    private String visa_type; // work,student or family visa type for immigrant

    public Immigrant(String person_id, String person_name, int person_age, String person_nationality,
                     String visa_type) {
        super(person_id, person_name, person_age, person_nationality);
        this.visa_type = visa_type;
    }

    public String getVisaType() { return visa_type; }
    public void setVisaType(String visa_type) { this.visa_type = visa_type; }

    @Override
    public void displayPersonInfo() {
        displayBasicInfo();
        System.out.println("Person Type: Immigrant");
        System.out.println("Visa Type: " + visa_type);
    }

    @Override
    public String getPersonType() {
        return "Immigrant";
    }

    @Override
    public String getSpecificInfo() {
        return visa_type;
    }
}

class AsylumSeeker extends Person { //Asylum seeker subclass
    private String asylum_reason;// reasons for seeking asylum like war or persecution
    private String application_status;//current status of application

    public AsylumSeeker(String person_id, String person_name, int person_age, String person_nationality,
                        String asylum_reason, String application_status) {
        super(person_id, person_name, person_age, person_nationality);
        this.asylum_reason = asylum_reason;
        this.application_status = application_status;
    }

    public String getAsylumReason() { return asylum_reason; }
    public void setAsylumReason(String asylum_reason) { this.asylum_reason = asylum_reason; }
    public String getApplicationStatus() { return application_status; }
    public void setApplicationStatus(String application_status) { this.application_status = application_status; }

    @Override
    public void displayPersonInfo() {
        displayBasicInfo();
        System.out.println("Person Type: Asylum Seeker");
        System.out.println("Asylum Reason: " + asylum_reason);
        System.out.println("Application Status: " + application_status);
    }

    @Override
    public String getPersonType() {
        return "AsylumSeeker";
    }

    @Override
    public String getSpecificInfo() {
        return asylum_reason + "," + application_status;
    }
}

interface RecordManageable { //interface to record and manage operations
    void addPersonRecord(Person person);
    void viewAllRecords();
    void updatePersonRecord(String person_id);
    void deletePersonRecord(String person_id);
    Person searchPersonRecord(String person_id);
    void saveDataToFile();
    void loadDataFromFile();
    void displaySummaryData();
}

class IBCMS implements RecordManageable { // system to implement recordmanageable interface
    private ArrayList<Person> person_records; //stores all person records
    private HashMap<String, Person> person_lookup; //for ID lookup
    private final String data_filename = "prakashGC_data.txt"; //data file name to store data
    private boolean data_modified = false; //flag to track if data has been modified

    private final Scanner input_scanner;

    public IBCMS(Scanner input_scanner) {
        this.input_scanner = input_scanner; //constructor to start data structure with startup message for the system
        person_records = new ArrayList<>();
        person_lookup = new HashMap<>();
        System.out.println("IBCMS System launched successfully.");
        loadDataFromFile(); // Automatically load data on startup
    }

    public ArrayList<Person> getPersonRecords() {//getter for checking if person records are empty
        return person_records;
    }


    private void autoSave() {   //automatic saving method for files after adding,removing or updating
        if (data_modified) {
            saveDataToFile();

        }
    }

    @Override //duplicate ID check from new person record
    public void addPersonRecord(Person person) {
        if (person_lookup.containsKey(person.getPersonId())) {
            System.out.println("Error: Person with ID " + person.getPersonId() + " already exists!");
            return;
        }
        person_records.add(person);
        person_lookup.put(person.getPersonId(), person);
        data_modified = true; // Mark data as modified
        System.out.println("Person record added successfully!");
        System.out.println("Total records: " + person_records.size());
        autoSave(); // Automatically save after adding
    }

    @Override //display all person records
    public void viewAllRecords() {
        if (person_records.isEmpty()) {
            System.out.println("No person records found in the system.");
            return;
        }
        System.out.println("\n---- ALL PERSON RECORDS ----");
        System.out.println("Total Records: " + person_records.size());
        System.out.println("==========================");
        for (int i = 0; i < person_records.size(); i++) {
            System.out.println("\n--- Record " + (i + 1) + " ---");
            person_records.get(i).displayPersonInfo();
            System.out.println("------------------------");
        }
    }

    @Override //update an existing person record by ID
    public void updatePersonRecord(String person_id) {
        Person person_to_update = person_lookup.get(person_id);
        if (person_to_update == null) {
            System.out.println("Person with ID " + person_id + " not found!");
            return;
        }
        System.out.println("Updating record for: " + person_to_update.getPersonName());

        System.out.print("Enter new name (current: " + person_to_update.getPersonName() + "): ");
        String new_name = input_scanner.nextLine();
        if (!new_name.trim().isEmpty()) {
            person_to_update.setPersonName(new_name);
        }

        System.out.print("Enter new age (current: " + person_to_update.getPersonAge() + "): ");// update name
        String age_input = input_scanner.nextLine();
        if (!age_input.trim().isEmpty()) {
            try {
                int new_age = Integer.parseInt(age_input); //update age
                person_to_update.setPersonAge(new_age);
            } catch (NumberFormatException e) { // error handling for incorrect age format
                System.out.println("Invalid age format. Keeping current age.");
            }
        }

        System.out.print("Enter new nationality (current: " + person_to_update.getPersonNationality() + "): ");//update nationality
        String new_nationality = input_scanner.nextLine();
        if (!new_nationality.trim().isEmpty()) {
            person_to_update.setPersonNationality(new_nationality);
        }

        if (person_to_update instanceof Visitor) { //update subclass fields
            Visitor visitor = (Visitor) person_to_update;
            System.out.print("Enter new visit purpose (current: " + visitor.getVisitPurpose() + "): ");
            String new_purpose = input_scanner.nextLine();
            if (!new_purpose.trim().isEmpty()) {
                visitor.setVisitPurpose(new_purpose);
            }
            System.out.print("Enter new visit duration (current: " + visitor.getVisitDuration() + "): ");
            String new_duration = input_scanner.nextLine();
            if (!new_duration.trim().isEmpty()) {
                visitor.setVisitDuration(new_duration);
            }
        } else if (person_to_update instanceof Immigrant) {
            Immigrant immigrant = (Immigrant) person_to_update;
            System.out.print("Enter new visa type (current: " + immigrant.getVisaType() + "): ");
            String new_visa = input_scanner.nextLine();
            if (!new_visa.trim().isEmpty()) {
                immigrant.setVisaType(new_visa);
            }
        } else if (person_to_update instanceof AsylumSeeker) {
            AsylumSeeker asylum_seeker = (AsylumSeeker) person_to_update;
            System.out.print("Enter new asylum reason (current: " + asylum_seeker.getAsylumReason() + "): ");
            String new_reason = input_scanner.nextLine();
            if (!new_reason.trim().isEmpty()) {
                asylum_seeker.setAsylumReason(new_reason);
            }
            System.out.print("Enter new application status (current: " + asylum_seeker.getApplicationStatus() + "): ");
            String new_status = input_scanner.nextLine();
            if (!new_status.trim().isEmpty()) {
                asylum_seeker.setApplicationStatus(new_status);
            }
        }
        data_modified = true; // this will mark data as modified
        System.out.println("Person record updated successfully!");
        autoSave();      // Auto save after updating data
    }

    @Override
    public void deletePersonRecord(String person_id) { //delete person record by ID
        Person person_to_delete = person_lookup.get(person_id);
        if (person_to_delete == null) {
            System.out.println("Person with ID " + person_id + " not found!");
            return;
        }
        person_records.remove(person_to_delete);
        person_lookup.remove(person_id);
        data_modified = true; // this mark data as modified
        System.out.println("Person record deleted successfully!");
        System.out.println("Remaining records: " + person_records.size());
        autoSave(); // Auto save after deleting data
    }

    @Override
    public Person searchPersonRecord(String person_id) { //search for a person by ID
        Person found_person = person_lookup.get(person_id);
        if (found_person != null) {
            System.out.println("\n=== PERSON FOUND ===");
            found_person.displayPersonInfo();
            System.out.println("==================");
            return found_person;
        } else {
            System.out.println("Person with ID " + person_id + " not found!");
            return null;
        }
    }

    public void searchByNameOrNationality(String search_term) { //search person by name or nationality
        ArrayList<Person> matching_persons = new ArrayList<>();
        for (Person person : person_records) {
            if (person.getPersonName().toLowerCase().contains(search_term.toLowerCase()) ||
                    person.getPersonNationality().toLowerCase().contains(search_term.toLowerCase())) {
                matching_persons.add(person);
            }
        }
        if (matching_persons.isEmpty()) {
            System.out.println("No persons found matching: " + search_term);
        } else {
            System.out.println("\n=== SEARCH RESULTS ===");
            System.out.println("Found " + matching_persons.size() + " matching records:");
            for (Person person : matching_persons) {
                System.out.println("\n--- Match ---");
                person.displayPersonInfo();
                System.out.println("-------------");
            }
        }
    }

    @Override
    public void saveDataToFile() { //save all records to file in CSV format
        try (BufferedWriter file_writer = new BufferedWriter(new FileWriter(data_filename))) {
            file_writer.write("PersonID,Name,Age,Nationality,PersonType,SpecificInfo");
            file_writer.newLine(); //csv header

            for (Person person : person_records) { // getter to write each record
                StringBuilder record_line = new StringBuilder();
                record_line.append(person.getPersonId()).append(",");
                record_line.append(person.getPersonName()).append(",");
                record_line.append(person.getPersonAge()).append(",");
                record_line.append(person.getPersonNationality()).append(",");
                record_line.append(person.getPersonType()).append(",");
                record_line.append(person.getSpecificInfo());
                file_writer.write(record_line.toString());
                file_writer.newLine();
            }
            data_modified = false;
            System.out.println("Data saved successfully to " + data_filename);
            System.out.println("Total records saved: " + person_records.size());
        } catch (IOException file_error) {
            System.out.println("Error saving data to file: " + file_error.getMessage());
        }
    }

    @Override
    public void loadDataFromFile() { // load all records from a file
        try {
            File data_file = new File(data_filename);
            if (!data_file.exists()) {
                data_file.createNewFile(); //create new file if it doesnot exist
                System.out.println("Created new data file: " + data_filename);
                return;
            }

            person_records.clear();  //clear existing data records
            person_lookup.clear();  // clear lookup map from system

            try (BufferedReader file_reader = new BufferedReader(new FileReader(data_filename))) {
                String file_line;
                int line_number = 0;
                int loaded_records = 0;

                while ((file_line = file_reader.readLine()) != null) {
                    line_number++;
                    if (line_number == 1) continue; // skip header and empty lines
                    if (file_line.trim().isEmpty()) continue;

                    String[] record_parts = file_line.split(",", -1);
                    if (record_parts.length < 6) {
                        System.out.println("Warning  ️⚠ Invalid record format at line " + line_number);
                        continue;    //skips invalid data records
                    }

                    String person_id = record_parts[0].trim(); //extracting fields from data record
                    String person_name = record_parts[1].trim();
                    int person_age = Integer.parseInt(record_parts[2].trim());
                    String person_nationality = record_parts[3].trim();
                    String person_type = record_parts[4].trim();

                    Person loaded_person = null;
                    //reconstructing correct subclass on the basis of type
                    if (person_type.equals("Visitor") && record_parts.length >= 7) {
                        loaded_person = new Visitor(person_id, person_name, person_age, person_nationality,
                                record_parts[5], record_parts[6]);
                    } else if (person_type.equals("Immigrant") && record_parts.length >= 6) {
                        loaded_person = new Immigrant(person_id, person_name, person_age, person_nationality,
                                record_parts[5]);
                    } else if (person_type.equals("AsylumSeeker") && record_parts.length >= 7) {
                        loaded_person = new AsylumSeeker(person_id, person_name, person_age, person_nationality,
                                record_parts[5], record_parts[6]);
                    }

                    if (loaded_person != null) {
                        person_records.add(loaded_person);
                        person_lookup.put(person_id, loaded_person);
                        loaded_records++;
                    }
                }
                System.out.println("Data loaded successfully from " + data_filename);
                System.out.println("Total records loaded: " + loaded_records);
            }
        } catch (IOException file_error) { // error handling for file
            System.out.println("Error loading data from file: " + file_error.getMessage());
        }
    }

    @Override //display summary about records
    public void displaySummaryData() {
        System.out.println("\n <<<< SYSTEM SUMMARY STATISTICS >>>>");
        System.out.println("Total Person Records: " + person_records.size());
        if (person_records.isEmpty()) {
            System.out.println("No data available for analysis.");
            return;
        }

        int visitor_count = 0;
        int immigrant_count = 0;
        int asylum_seeker_count = 0;
        int min_age = Integer.MAX_VALUE;
        int max_age = Integer.MIN_VALUE;
        HashMap<String, Integer> nationality_counts = new HashMap<>();

        for (Person person : person_records) {
            if (person instanceof Visitor) visitor_count++;
            else if (person instanceof Immigrant) immigrant_count++;
            else if (person instanceof AsylumSeeker) asylum_seeker_count++;

            int age = person.getPersonAge();
            min_age = Math.min(min_age, age);
            max_age = Math.max(max_age, age);

            String nationality = person.getPersonNationality();
            nationality_counts.put(nationality, nationality_counts.getOrDefault(nationality, 0) + 1);
        }

        System.out.println("\n--- PERSON TYPE BREAKDOWN ---");
        System.out.println("Visitors: " + visitor_count);
        System.out.println("Immigrants: " + immigrant_count);
        System.out.println("Asylum Seekers: " + asylum_seeker_count);

        System.out.println("\n--- AGE STATISTICS ---");
        System.out.println("Minimum Age: " + min_age);
        System.out.println("Maximum Age: " + max_age);

        System.out.println("\n--- NATIONALITY BREAKDOWN ---");
        for (Map.Entry<String, Integer> entry : nationality_counts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " persons");
        }
        System.out.println("================================\n");
    }

    //initialising system with sample data
    public void initializeWithSampleData() {
        System.out.println("Initializing system with sample data...");
        Visitor sample_visitor = new Visitor("VIS001", "Sheldon Cooper", 19, "USA", "Tourism", "4 weeks");
        addPersonRecord(sample_visitor);

        Immigrant sample_immigrant = new Immigrant("IMM001", "Maria Garcia", 35, "Mexico", "Work Visa");
        addPersonRecord(sample_immigrant);

        AsylumSeeker sample_asylum_seeker = new AsylumSeeker("ASY001", "Mohammad Gabbar", 26, "Pakistan", "War", "Verified");
        addPersonRecord(sample_asylum_seeker);

        System.out.println("Sample data initialized successfully!");

    }

    // Graceful shutdown method
    public void shutdown() {
        autoSave(); // Save any unsaved changes before shutdown
        System.out.println("IBCMS system shutdown completed.");
    }
}
