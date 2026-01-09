pipeline {
    agent any

    // Environment variables for the pipeline
    environment {
        APP_NAME = 'calculator-app'
        APP_VERSION = '1.0.0'
        DOCKER_IMAGE = "${APP_NAME}"
        DOCKER_TAG = "${BUILD_NUMBER}"
    }


    // Tool configuration
    tools {
        maven 'Maven-3.9'  // Configure this in Jenkins Global Tool Configuration
    }

    stages {
        stage('Initialize') {
            steps {
                echo '==========================================='
                echo '   Jenkins Pipeline - Calculator Demo'
                echo '==========================================='
                echo "Application: ${APP_NAME} v${APP_VERSION}"
                echo "Build Number: ${BUILD_NUMBER}"
                echo "Environment: ${params.ENVIRONMENT}"
                echo "Workspace: ${WORKSPACE}"
                echo '==========================================='
            }
        }

        stage('Checkout') {
            steps {
                echo 'Checking out source code...'

                // For demo with local files (already in workspace)
                // In real scenario, use: checkout scm
                git url: 'https://github.com/TRNG-00002321/calculator-app.git', branch: 'main'
                sh '''
                            echo "Current directory contents:"
                            ls -la
                            echo ""
                            echo "Java source files:"
                            find . -name "*.java" -type f 2>/dev/null || true
                        '''
            }
        } // stage check out

        stage('Build') {
            steps {
                echo 'Building application with Maven...'

                sh '''
                                        echo "Maven Wrapper version:"
                                        chmod +x mvnw
                                        ./mvnw --version
                                        echo ""
                                        echo "Compiling application..."
                                        ./mvnw clean compile -B
                                    '''
            }
        } // stage build

        stage('Test') {
//             when {
//                 expression { params.SKIP_TESTS == false }
//             }
            steps {
                echo 'Running unit tests...'


                sh '''
                        echo "Executing JUnit tests..."
                        mvn test -B
                    '''

            }
            post {
                always {
                    // Publish JUnit test results
                    junit(
                            testResults: 'target/surefire-reports/*.xml',
                            allowEmptyResults: true
                    )
                }
                success {
                    echo '✅ All tests passed!'
                }
                failure {
                    echo '❌ Some tests failed!'
                }
            }
        } // stage test

        stage('Package') {
            steps {
                echo 'Packaging application...'

                sh '''
                        echo "Creating JAR package..."
                        mvn package -DskipTests -B
                        echo ""
                        echo "Build artifacts:"
                        ls -la target/*.jar
                    '''

            }
        } // stage package
        stage('Archive Artifacts') {
            steps {
                echo 'Archiving build artifacts...'

                archiveArtifacts(
                        artifacts: 'target/*.jar',
                        fingerprint: true,
                        allowEmptyArchive: false
                )
            }
        } // stage archive

        stage('Build Docker Image') {
//            when {
//                expression { params.BUILD_DOCKER == true }
//            }
            steps {
                echo "Building Docker image: ${DOCKER_IMAGE}:${DOCKER_TAG}"

                sh '''
                        docker build -t ${DOCKER_IMAGE}:${DOCKER_TAG} .
                        docker tag ${DOCKER_IMAGE}:${DOCKER_TAG} ${DOCKER_IMAGE}:latest
                        
                        echo ""
                        echo "Docker images built:"
                        docker images | grep ${DOCKER_IMAGE} || true
                    '''

            }
        } // stage docker image

        stage('Test Docker Image') {
//            when {
//                expression { params.BUILD_DOCKER == true }
//            }
            steps {
                echo 'Testing Docker container...'

                sh '''
                    # Run the container
                    echo "Running container for test..."
                    docker run --rm ${DOCKER_IMAGE}:${DOCKER_TAG}
                    
                    echo ""
                    echo "Container test completed successfully!"
                '''
            } // stage Test Docker Image
        }

    } // stages end


}