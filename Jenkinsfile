node {
    
    stage('Preparation') { // for display purposes
        def config_base_name = "dd2"
        def end_status = "Successemo29"
        def koko = "kokoloko"
        sh(script: """
        echo "this is the hostname:"  \$(hostname)
        #tmpfile="\$(mktemp /tmp/dockerEnvXXXXX)"
        #echo "test.status=${end_status}" >> "\$tmpfile"
        #echo "test.name=${config_base_name}" >> "\$tmpfile"
        #cat "\$tmpfile"
        #cat "\$tmpfile" | docker run --rm -e OTEL_EXPORTER_OTLP_ENDPOINT dell/opentelemetry-cli:0.4.0 metric counter tridevlab.test-counter -a "test.status=${end_status}"
        #docker run --rm -v /tmp:/tmp -e OTEL_EXPORTER_OTLP_ENDPOINT dell/opentelemetry-cli:0.4.0 metric counter tridevlab.test-counter --attribute-file "\$tmpfile"
        #rm "\$tmpfile"
        
        file_path=/tmp/attributes.txt
        #if [ -f "\$file_path" ]; then
        #    echo "file \$file_path exists"
        #else
        #    echo "file \$file_path does not exists creating it"
        #    touch "\$file_path"
        #fi
        echo "test.status=${end_status}" >> "\$file_path"
        echo "test.name=${config_base_name}" >> "\$file_path"
        echo "str[]:my-array=a,c" >> "\$file_path"
        docker run --rm -v /tmp:/tmp -e OTEL_EXPORTER_OTLP_ENDPOINT dell/opentelemetry-cli:0.4.0 metric counter tridevlab.test-counter --attribute-file "\$file_path"
        rm "\$file_path"
        
        
        
        """, label: "Report OTel", returnStatus: true)
    }
    
}
