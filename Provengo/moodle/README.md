# helloprovengo

---
2024-02-19 20:44:26
User

Provengo project for spec-ing and testing my system.


## Important Files

* README.md This file.
* [config](config) Configuration files and administrative data.
    * [provengo.yml](config/provengo.yml) Main Configuration file
    * [hooks](config/hooks) Hook scripts (pre/post/...)
* [spec](spec) The code creating the specification space lives here. Organized by language.
    * [js](spec/js) JavaScript files
      * [hello-world.js](spec/js/hello-world.js) Initial model file.
* [meta-spec](meta-spec) Code for working with the specification space
    * [ensemble-code.js](meta-spec/ensemble-code.js) Sample code for generating test optimized test suites (ensembles)
    * [book-writer.js](meta-spec/book-writer.js) Sample code for generating test books
    * [script-writer.js](meta-spec/script-writer.js) Code for generating test scripts.
* [lib](lib) Place to store JavaScript libraries. Loaded first.
* [data](data) Place to store data files. Loaded second (so you can use library code to in your data).
    * [data.js](data/data.js) Sample data file.
* [products](products) Artifacts generated from the spec (such as run logs, scripts, and test-books) will be stored here. Much like `build` directories in other platforms, this directory can be ignored by version control systems (e.g. `git`).


## Useful Commands

⚠️ NOTE: In the below listings, we assume that `provengo` is in the system's PATH variable, and that `C:\Repositories\sqe-system-tesing\Provengo\helloprovengo` is the path to this directory.

For full documentation go to [https://docs.provengo.tech](docs.provengo.tech).

### Randomized Run 

Perform a single run through the specification. Good for "Sanity checks", i.e. to see examples of what can happen.

    provengo run --dry C:\Repositories\sqe-system-tesing\Provengo\helloprovengo


### Visualize the Spec

Draw the specification in a PDF file.

    provengo analyze -f pdf C:\Repositories\sqe-system-tesing\Provengo\helloprovengo


⚠️ NOTE: This requires [Graphviz](http://graphviz.org) to be installed.


### Sample Runs from the Spec

Sample 10 scenarios into a file. The scenarios are stored in a file called `samples.json` (this can be changed using the `-o`/`--output-file` switch).

    provengo sample --overwrite --size 10 C:\Repositories\sqe-system-tesing\Provengo\helloprovengo


### Create an Optimized Test Suite

Generate a test suite of 5 tests that provides a good coverage of items in the [GOALS](z-ranking.js#L18) array.

**Requires running `sample` first** (the previous command)**.**

    provengo ensemble --size 5 C:\Repositories\sqe-system-tesing\Provengo\helloprovengo

#### Visualize the Spec and the Suite

Draw the specification, and highlight the traces in the optimized test suite create by the previous command.

    provengo analyze -f pdf --highlight ensemble.json C:\Repositories\sqe-system-tesing\Provengo\helloprovengo

### Create Test Scripts for Third Party Systems

Converts the runs in `ensemble.json` to automation test scripts.

    provengo gen-scripts -s ensemble.json C:\Repositories\sqe-system-tesing\Provengo\helloprovengo

## AI code completion
To enable AI code completion, please use a code completion plugin (e.g., [GitHub Copilot](https://github.com/features/copilot)) and keep open the files inside the [config/ai](config/ai) folder.


## ⚠️ ATTENTION - Test Configuration:
To run the tests correctly, you must select the appropriate TEST_TYPE in data.js.

* The system supports three modes:
    1. DOMAIN_SPECIFIC: Tests the basic student submission and teacher limitation flow
    2. TWO_WAY: Tests the interaction between teacher limitations and student submissions
    3. RESET: Resets the system to its initial state after running DOMAIN_SPECIFIC tests (Note: This is not a test type, but rather a system reset mechanism)

* To select a test type:
    1. Uncomment the desired TEST_TYPE line in data.js
    2. Comment out the other TEST_TYPE lines
    3. The RESET type should only be used after running DOMAIN_SPECIFIC tests

* For example:
        //let TEST_TYPE = TEST_TYPES.DOMAIN_SPECIFIC; 
        let TEST_TYPE = TEST_TYPES.TWO_WAY;  // Currently testing TWO_WAY
        //let TEST_TYPE = TEST_TYPES.RESET;