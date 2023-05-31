@Library('my-shared-library') _
node {
    


    stage('Preparation') { // for display purposes
         
        def labels = "-l MLK-EX1\\|MLK-EX2\\|MLK-EX3\\|MLK-EX4,NVMeOF-FC,PhysicalLG,fc"
        def appliance = "WK-D0028"

        // def labels = "-l EX\\|MLK\\|Riptide,CopperBlade\\|LightBlade"
        // def appliance = "RT-D0201-RT-D0197-federation-TAG"
        
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
