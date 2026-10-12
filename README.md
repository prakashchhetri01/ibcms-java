# Immigration and Border Control Management System (IBCMS)

A Java console application demonstrating object-oriented record management for visitors, immigrants and asylum seekers in a simulated immigration scenario.

## Project background

Created by **Prakash Bahadur G C** for **XFX1063: Software Design & Development**, International Study Centre, University of Huddersfield. Original portfolio submitted on **26 July 2025**.

## Features

- Add, view, update and delete person records.
- Reject duplicate person IDs when adding records.
- Search by ID or by name/nationality keyword.
- Manage type-specific fields for visitors, immigrants and asylum seekers.
- Display record counts, minimum/maximum ages and nationality counts.
- Automatically save after changes and load records on startup.
- Initialize three demonstration records on the first run.

## Design and skills demonstrated

| Component | Purpose |
| --- | --- |
| `Person` | Abstract superclass with encapsulated common fields. |
| `Visitor`, `Immigrant`, `AsylumSeeker` | Subclasses that implement type-specific information. |
| `RecordManageable` | Interface defining record operations. |
| `IBCMS` | Record management, searches, summaries and file storage. |
| `IBCMSApp` | Console entry point and menu. |
| `ArrayList<Person>` | Sequential record storage and keyword searches. |
| `HashMap<String, Person>` | Lookup by person ID. |

Skills include abstraction, inheritance, polymorphism, interfaces, encapsulation, Java collections, exception handling and file I/O. Classes are retained together in one source file, as in the original portfolio.

## Run locally

Install a Java Development Kit (JDK 8 or newer). From the repository root:

```bash
mkdir -p build
javac -encoding UTF-8 -d build src/IBCMSApp.java
java -cp build IBCMSApp
```

On Windows, create `build` in File Explorer or use `mkdir build`, then run the same Java commands.

With JDK 11 or newer, you can also run directly:

```bash
java src/IBCMSApp.java
```

## Menu

| Option | Action |
| --- | --- |
| 1 | View all records |
| 2 | Add a new record |
| 3 | Update a record |
| 4 | Delete a record |
| 5 | Search by ID |
| 6 | Search by name or nationality |
| 7 | Display summary statistics |
| 0 | Exit |

Press Enter after each operation to return to the menu. During updates, leave a field blank to retain its current value.

## Data storage

The application creates `prakashGC_data.txt` in the current working directory. It stores comma-separated records with a header and reloads them on startup. The number of fields varies by person type. This generated file is excluded from Git.

Use fictional records for this demonstration. No original report screenshots or student identifier are included in this repository.

## Changes since the original portfolio

- Shared one input scanner between the menu and update operation.
- Seeded sample records only when the data file does not yet exist, preserving an intentionally empty saved database.
- Cleared the modification flag only after a successful save.
- Preserved trailing empty fields when splitting stored records.
- Placed the entry-point class first to support Java source-file mode.

## Limitations and future improvements

This is an educational prototype, not an operational immigration system. It has no authentication, authorization or encryption. Input validation is limited, and IDs are case-sensitive. Storage does not support CSV quoting: commas in field values can corrupt reloads, and invalid age values in a manually edited data file can cause a loading error. Writes replace the data file directly rather than using an atomic save.

Future improvements include stronger validation, robust CSV parsing or database storage, unique immutable identifiers, separating classes into files, automated tests, access controls and a graphical interface.
