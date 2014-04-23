package org.agileworks.fastldr

import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

class H2IntegrationTest extends Specification {

    @Shared
    Sql sql

    void setupSpec() {
        sql = Sql.newInstance("jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:sql/init.sql'", 'sa', null, 'org.h2.Driver')
    }

    def "Import sample data to H2"() {
        setup: "Load data using fastldr to database"
        App.main("help")
        expect:
        def rows = sql.rows("select id, name, url from TEST_TABLE where id = $id")

        rows.size() == 1

        def row = rows[0]
        name == row.name
        url == row.url

        where:

        name | url | id
        'Groovy'     | 'http://groovy.codehaus.org' | 10
        'AgileWorks' | 'http://www.agileworks.org'  | 20
    }
}