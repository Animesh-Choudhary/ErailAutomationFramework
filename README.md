Perfect! Based on your input, I’ll update the `README.md` to highlight that the framework is **extensible** and can handle **multiple websites** by configuring individual site details through `SiteConfig` in the `utils` package.

---

### 🛠️ Updated `README.md` snippet:

```markdown
# Erail Automation Framework 🚆

This is a Java-based Test Automation Framework built using **Selenium**, **TestNG**, and **Maven**. It automates the verification of features on [erail.in](https://erail.in), a popular train inquiry website in India.

## 🔧 Technologies Used
- Java
- Selenium WebDriver
- TestNG
- Maven
- ExtentReports
- Apache POI (for Excel reading)
- Git & GitHub for version control

## 🔄 Extensible Framework for Multiple Websites
This framework is **designed to be easily extensible**, capable of handling automation for **multiple websites**.  
Each site’s unique configuration — such as XPath locators, base URLs, and more — can be customized via the `SiteConfig.java` class located in the `utils` package.

Just add the site's unique identifiers, and the same test structure will adapt accordingly. 🚀

## 📁 Project Structure
```
ErailAutomationFramework/
├── pom.xml                    # Maven dependencies and build config
├── testng.xml                 # TestNG suite configuration
├── src/                       # (inferred) contains all source code
│   ├── base/                  # Base classes for test setup
│   ├── pages/                 # Page Object Model classes
│   ├── testcases/             # Test classes
│   ├── utils/                 # Utility files (Excel, config)
│   │   └── SiteConfig.java    # Config file to support multi-site automation
│   └── context/               # Shared test context
├── expectedStations.xlsx      # Test data for verification
├── extent-config.json         # Extent report styling
└── target/                    # Compiled code and test reports
```

## 🧪 How to Run Tests
1. Clone this repo:
   ```bash
   git clone https://github.com/Animesh-Choudhary/ErailAutomationFramework.git
   cd ErailAutomationFramework
   ```
2. Build the project:
   ```bash
   mvn clean install
   ```
3. Run the tests:
   ```bash
   mvn test
   ```

## 📊 Reports
Test execution reports are generated in:
```
target/surefire-reports/
```

## 📌 Author
**Animesh Kumar Choudhary**  
[LinkedIn]([https://www.linkedin.com/in/animesh-kumar-choudhary](https://www.linkedin.com/in/animesh-kumar-3a6696248))
