package org.formation;

import org.formation.AllAccountsQuery.Data;
import org.jetbrains.annotations.NotNull;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

public class ExecuteQuery {

	public static void main(String[] args) {
		ApolloClient apolloClient = ApolloClient.builder()
		        .serverUrl("http://localhost:8080/graphql")
		        .build();

		// Then enqueue your query
		apolloClient.query(new AllAccountsQuery())
		        .enqueue(new ApolloCall.Callback<AllAccountsQuery.Data>() {
		            
		            @Override
					public void onResponse(Response<Data> response) {
						response.getData().accounts().stream().forEach(System.out::println);
						
					}

					@Override
		            public void onFailure(@NotNull ApolloException e) {
		                System.out.println("Apollo Error"+ e);
		            }
		        });



	}

}
