/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.bahir.Utils;

import org.apache.kudu.ColumnSchema;
import org.apache.kudu.Schema;
import org.apache.kudu.Type;
import org.apache.kudu.client.CreateTableOptions;
import org.apache.kudu.client.KuduClient;
import org.apache.kudu.client.KuduException;

import java.util.ArrayList;
import java.util.List;


public class CreateKuduTable {
    public static void main(String[] args) throws KuduException {

        String tableName = ""; // TODO insert table name
        String host = "localhost";

        KuduClient client = new KuduClient.KuduClientBuilder(host).build();

        List<ColumnSchema> columns = new ArrayList(2);
        columns.add(new ColumnSchema.ColumnSchemaBuilder("valueInt", Type.INT32)
                .key(true)
                .build());
        columns.add(new ColumnSchema.ColumnSchemaBuilder("valueString", Type.STRING)
                .build());
        List<String> rangeKeys = new ArrayList<>();
        rangeKeys.add("valueInt");
        Schema schema = new Schema(columns);
        client.createTable(tableName, schema,
                new CreateTableOptions().setRangePartitionColumns(rangeKeys).addHashPartitions(rangeKeys, 4));
        System.out.println("Table \"" + tableName + "\" created succesfully");

    }
}