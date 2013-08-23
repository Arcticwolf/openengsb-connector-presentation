openengsb-connector-presentation
================================

This project's purpose is the second half of the thesis validation code of Felix Mayerhuber, containing test code for the model transformation unit of the OpenEngSB project. It contains all transformation definitions + the logic for the thesis validation elements.

To run the whole validation a few steps are needed:
- install and start an OpenEngSB instance
- clone both, the domain and the connector of the presentation bundles & clean install them
- in the running OpenEngSB instance you enter the following two lines:
1. feature:repo-add mvn:org.openengsb.domain/org.openengsb.domain.presentation/3.0.0-SNAPSHOT/xml/features
2. feature:install openengsb-presentation-bundle
- after that, all needed bundles for the validation have been installed and are ready to be used
- visit with your browser the link localhost:8090/openengsb and login with the data admin/password
- check out the Test Client on the left side. Now a list of different domains are listed which are active
- search for the presentation domain and create a new instance of the corresponding connector
- now you can select the newly created connector and call all available validation elements. The validation will run and the result is printed
