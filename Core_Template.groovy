@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         
        def labels = "-l EX\\|Riptide\\|MLK"
        def appliance = "RT-D3108"
        
        def tagsList = libOtel.getListTags(labels, appliance)
        def generations = libOtel.getListGenertions(appliance)
        
        sh(script: """
        docker run --rm -e OTEL_EXPORTER_OTLP_ENDPOINT \
            dell/opentelemetry-cli:0.4.0 \
            metric counter tridevlab.test-counter \
            -a "str[]:test.tags=${tagsList}" \
            -a "str[]:test.generation=${generations}"
    """, label: "Report OTel", returnStatus: true)
    }
    
}
