``Basic Annotations``
	
	• @ConfigurationProperties(value = "limits-serice") - To read the data from the property file, 
		○ The property file should have entries like 
			limits-serice.minimum = 2
			limits-serice.maximim = 900
		○ And the class should have attributes  like minimum and maximum
	•  @EnableConfigServer - To make a service a cloud config server




	1. Set up the git repo and file with properties
		a. Names of files should be like
			i. limits-service.properties
			ii. limits-service-qa.properties
			iii. limits-service-dev.properties
	2. Set up the server
		a. Use spring-cloud-config-server starter
		b. Include @EnableConfigServer in the main class of server
		c. Set the spring.cloud.config.server.git.uri= // path to the properties file set earlier in S1
	3. Set up the client
		a. We need the spring cloud config client added in  the microservice 
		b. Set the following in the application.properties
		spring.config.import=optional:configserver:http://localhost:8888 - Path in which spring cloud server is set up
		spring.cloud.config.profile=prod - Sub name of the .properties file to be used
		spring.cloud.config.name=limits-service - name of the file to be used
