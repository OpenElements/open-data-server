package com.openelements.opendata.pullrequests;

import com.openelements.opendata.base.DtoMapper;
import org.mapstruct.Mapper;

@Mapper
public interface PullRequestMapper extends DtoMapper<PullRequestDTO, PullRequest> {
}
